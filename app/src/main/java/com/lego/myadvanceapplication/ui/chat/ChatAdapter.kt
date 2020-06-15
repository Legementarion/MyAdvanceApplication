package com.lego.myadvanceapplication.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.data.models.Message
import com.lego.myadvanceapplication.domain.MessageLock
import com.lego.myadvanceapplication.ui.utils.loadCropImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_message_shared.view.*

class ChatAdapter(options: FirebaseRecyclerOptions<Message>, private val type: ChatType) :
    FirebaseRecyclerAdapter<Message, ChatAdapter.PrivateMessageViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrivateMessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            ChatType.Public -> SharedMessageViewHolder(
                inflater.inflate(
                    R.layout.item_chat_message_shared,
                    parent,
                    false
                )
            )
            else -> PrivateMessageViewHolder(
                inflater.inflate(
                    R.layout.item_chat_message,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        viewHolder: PrivateMessageViewHolder,
        position: Int,
        message: Message
    ) {
        getItem(position).let { viewHolder.bindData(it) }
    }


    open inner class PrivateMessageViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        open fun bindData(message: Message) {

            with(containerView) {

                if (message.message != null) {
                    tvMessage.text = MessageLock.retrieveMessage(message.message)
                    tvMessage.visibility = TextView.VISIBLE
//                    messageImageView.visibility = ImageView.GONE
                }
                //todo upload images
//                else if (message.imageUrl != null) {
////                    val imageUrl: String = message.imageUrl
////                    if (imageUrl.startsWith("gs://")) {
////                        val storageReference = FirebaseStorage.getInstance()
////                            .getReferenceFromUrl(imageUrl)
////                        storageReference.downloadUrl.addOnCompleteListener { task ->
////                            if (task.isSuccessful) {
////                                val downloadUrl: String =
////                                    task.result.toString()
//////                                Glide.with(messageImageView.context)
//////                                    .load(downloadUrl)
//////                                    .into(messageImageView)
////                            } else {
////                                Log.w(
////                                    "Getting download url was not successful.",
////                                    task.exception
////                                )
////                            }
////                        }
////                    } else {
//////                        Glide.with(messageImageView.context)
//////                            .load(message.imageUrl)
//////                            .into(messageImageView)
////                    }
//////                    messageImageView.visibility = ImageView.VISIBLE
////                    tvMessage.visibility = TextView.GONE
////                }

                btnEdit.setOnClickListener {
                    val popup = PopupMenu(containerView.context, btnEdit)
                    popup.inflate(R.menu.todo_menu)

                    popup.setOnMenuItemClickListener {
                        return@setOnMenuItemClickListener when (it.itemId) {
                            R.id.navigation_drawer_item1 ->
                                true
                            R.id.navigation_drawer_item2 ->
                                true
                            R.id.navigation_drawer_item3 ->
                                true
                            else -> false
                        }
                    }
                    popup.show()
                }


            }
        }
    }

    inner class SharedMessageViewHolder(override val containerView: View) :
        PrivateMessageViewHolder(containerView) {

        override fun bindData(message: Message) {
            super.bindData(message)
            with(containerView) {

                tvAuthor.text = message.user

                message.photoUrl?.let { ivAuthor.loadCropImage(it) } ?: run {
                    ivAuthor.setImageDrawable(
                        ContextCompat.getDrawable(
                            ivAuthor.context,
                            R.drawable.ic_account_circle_black_36dp
                        )
                    )
                }
            }
        }
    }

}