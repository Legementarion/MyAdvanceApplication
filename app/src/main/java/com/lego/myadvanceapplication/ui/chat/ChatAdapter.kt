package com.lego.myadvanceapplication.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.data.models.Message
import com.lego.myadvanceapplication.domain.MessageLock
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatAdapter(options: FirebaseRecyclerOptions<Message>) :
    FirebaseRecyclerAdapter<Message, ChatAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(
            inflater.inflate(
                R.layout.item_chat_message,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int, message: Message) {
        getItem(position).let { viewHolder.bindData(it) }
    }

    inner class MessageViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(message: Message) {
            with(containerView) {
                if (message.message != null) {
                    messageTextView.text = MessageLock.retrieveMessage(message.message)
                    messageTextView.visibility = TextView.VISIBLE
//                    messageImageView.visibility = ImageView.GONE
                } else if (message.imageUrl != null) {
                    val imageUrl: String = message.imageUrl
                    if (imageUrl.startsWith("gs://")) {
                        val storageReference = FirebaseStorage.getInstance()
                            .getReferenceFromUrl(imageUrl)
                        storageReference.downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUrl: String =
                                    task.result.toString()
//                                Glide.with(messageImageView.context)
//                                    .load(downloadUrl)
//                                    .into(messageImageView)
                            } else {
                                Log.w(
                                    "Getting download url was not successful.",
                                    task.exception
                                )
                            }
                        }
                    } else {
//                        Glide.with(messageImageView.context)
//                            .load(message.imageUrl)
//                            .into(messageImageView)
                    }
//                    messageImageView.visibility = ImageView.VISIBLE
                    messageTextView.visibility = TextView.GONE
                }
                messengerTextView.text = message.user
                if (message.photoUrl == null) {
                    messengerImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            messengerImageView.context,
                            R.drawable.ic_account_circle_black_36dp
                        )
                    )
                } else {
                    Glide.with(messengerImageView.context)
                        .load(message.photoUrl)
                        .into(messengerImageView)
                }
            }
        }
    }

}