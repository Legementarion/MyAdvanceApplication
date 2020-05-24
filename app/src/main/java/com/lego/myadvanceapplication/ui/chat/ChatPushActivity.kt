package com.lego.myadvanceapplication.ui.chat

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.data.models.Message
import com.lego.myadvanceapplication.ui.SignInActivity
import kotlinx.android.synthetic.main.activity_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ChatPushActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by viewModel()

    // Firebase instance variables
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabaseReference: DatabaseReference
    private lateinit var firebaseAdapter: ChatAdapter
    private var firebaseUser: FirebaseUser? = null

    private var mUsername: String = ANONYMOUS
    private var photoUrl: String? = null
    private val mSharedPreferences: SharedPreferences? = null

    companion object {
        private const val TAG = "ChatPushActivity"
        const val MESSAGES_CHILD = "TODO"
        private const val REQUEST_INVITE = 1
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
        const val DEFAULT_MSG_LENGTH_LIMIT = 10
        const val ANONYMOUS = "anonymous"
        private const val MESSAGE_SENT_EVENT = "message_sent"
        private const val MESSAGE_URL = "http://friendlychat.firebase.google.com/message/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        } else {
            mUsername = firebaseUser?.displayName ?: ANONYMOUS
            firebaseUser?.photoUrl?.let {
                photoUrl = it.toString()
            }

        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        rvMessage.layoutManager = linearLayoutManager


        // New child entries
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
        val parser: SnapshotParser<Message?> =
            SnapshotParser<Message?> { snapshot ->
                val message: Message? = snapshot.getValue(Message::class.java)
                message?.id = snapshot.key
                message!!
            }

        val messagesRef: DatabaseReference = firebaseDatabaseReference.child(MESSAGES_CHILD)
        val options: FirebaseRecyclerOptions<Message> = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, parser)
            .build()

        firebaseAdapter = ChatAdapter(options)

        firebaseAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val messageCount: Int = firebaseAdapter.itemCount
                val lastVisiblePosition: Int =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                    positionStart >= messageCount - 1 &&
                    lastVisiblePosition == positionStart - 1
                ) {
                    rvMessage.scrollToPosition(positionStart)
                }
            }
        })

        rvMessage.adapter = firebaseAdapter


        btnSend.setOnClickListener {
            val message = Message(
                System.currentTimeMillis().toString(),
                etMessage.text.toString(),
                mUsername,
                System.currentTimeMillis().toString(),
                photoUrl,
                null /* no image */
            )
            firebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(message)
            etMessage.setText("")
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.e(
            "onActivityResult: requestCode=$requestCode, resultCode=$resultCode"
        )
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val uri = data.data
                    Timber.e("Uri: %s", uri.toString())
                    val tempMessage = Message(
                        null, mUsername, photoUrl,
                        LOADING_IMAGE_URL
                    )
                    firebaseDatabaseReference.child(MESSAGES_CHILD).push()
                        .setValue(tempMessage) { databaseError, databaseReference ->
                            if (databaseError == null) {
                                val key = databaseReference.key
                                val storageReference =
                                    FirebaseStorage.getInstance()
                                        .getReference(firebaseUser!!.uid)
                                        .child(key!!)
                                        .child(uri!!.lastPathSegment!!)
                                putImageInStorage(storageReference, uri, key)
                            } else {
                                Timber.e(databaseError.toException())
                            }
                        }
                }
            }
        }
    }

    private fun putImageInStorage(
        storageReference: StorageReference,
        uri: Uri,
        key: String
    ) {
        storageReference.putFile(uri).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.metadata!!.reference!!.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val message = Message(
                            null, mUsername, photoUrl,
                            it.result.toString()
                        )
                        firebaseDatabaseReference.child(MESSAGES_CHILD)
                            .child(key)
                            .setValue(message)
                    }
                }
            } else {
                Timber.e(task.exception)
            }
        }
    }

    override fun onPause() {
        firebaseAdapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        firebaseAdapter.startListening()
    }

}