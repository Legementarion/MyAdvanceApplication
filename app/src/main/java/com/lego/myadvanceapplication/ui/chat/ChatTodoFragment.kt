package com.lego.myadvanceapplication.ui.chat

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.core.notification.MyFirebaseMessagingService
import com.lego.myadvanceapplication.data.models.Message
import com.lego.myadvanceapplication.domain.MessageLock
import kotlinx.android.synthetic.main.chat_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ChatTodoFragment : Fragment() {

    companion object {
        const val MESSAGES_CHILD = "TODO"
        private const val REQUEST_INVITE = 1
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
        const val DEFAULT_MSG_LENGTH_LIMIT = 10
        const val ANONYMOUS = "anonymous"
        private const val MESSAGE_SENT_EVENT = "message_sent"
        private const val MESSAGE_URL = "http://friendlychat.firebase.google.com/message/"

        private const val CHAT_EXTRA = "CHAT_EXTRA"

        fun newInstance(chatType: ChatType): ChatTodoFragment {
            return ChatTodoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CHAT_EXTRA, chatType)
                }
            }
        }
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabaseReference: DatabaseReference
    private lateinit var firebaseAdapter: ChatAdapter
    private var firebaseUser: FirebaseUser? = null

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: ChatViewModel by viewModel()

    private var username: String = ANONYMOUS
    private var photoUrl: String? = null
    private var path = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser
        username = firebaseUser?.displayName ?: ANONYMOUS
        var type: ChatType = ChatType.Public
        arguments?.getSerializable(CHAT_EXTRA)?.let {
            type = it as ChatType
            path += when (type) {
                ChatType.My -> {
                    "$MESSAGES_CHILD/$username"
                }
                else -> {
                    "$MESSAGES_CHILD/all"
                }
            }
        }

        showEmptyState()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        firebaseUser?.photoUrl?.let {
            photoUrl = it.toString()
        }

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
        rvMessage.layoutManager = linearLayoutManager

        // New child entries
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
        val parser: SnapshotParser<Message> =
            SnapshotParser<Message> { snapshot ->
                val message: Message? = snapshot.getValue(Message::class.java)
                message?.id = snapshot.key
                message!!
            }

        val messagesDBRef: DatabaseReference =
            firebaseDatabaseReference.child(path)
        val options: FirebaseRecyclerOptions<Message> = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesDBRef, parser)
            .build()

        firebaseAdapter = ChatAdapter(options, type)


        firebaseAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val messageCount: Int = firebaseAdapter.itemCount
                val lastVisiblePosition: Int =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                checkState()
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
            val token = sharedPreferences.getString(MyFirebaseMessagingService.TOKEN_TOPIC, "")
            val message = Message(
                System.currentTimeMillis().toString(),
                MessageLock.encodeMessage(etMessage.text.toString()),
                username,
                System.currentTimeMillis().toString(),
                token,
                photoUrl,
                null /* no image */
            )
            firebaseDatabaseReference.child(path)
                .push().setValue(message)
            etMessage.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val uri = data.data
                    Timber.d("Uri: %s", uri.toString())
                    val tempMessage = Message(
                        null,
                        user = username,
                        photoUrl = photoUrl,
                        imageUrl = LOADING_IMAGE_URL
                    )
                    firebaseDatabaseReference.child(path).push()
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

    private fun showEmptyState(visibility: Boolean = false) {
        tvEmptyState.visibility = if (visibility) View.GONE else View.VISIBLE
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
                            null, user = username, photoUrl = photoUrl,
                            imageUrl = it.result.toString()
                        )
                        firebaseDatabaseReference.child(path)
                            .child(key)
                            .setValue(message)
                    }
                }
            } else {
                Timber.e(task.exception)
            }
        }
    }

    private fun checkState() {
        if (firebaseAdapter.itemCount > 0) {
            showEmptyState(true)
        } else {
            showEmptyState()
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