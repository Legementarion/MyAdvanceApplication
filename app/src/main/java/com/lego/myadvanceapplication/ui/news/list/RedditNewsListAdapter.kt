package com.lego.myadvanceapplication.ui.news.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.data.RedditApi.Companion.BASE_URL
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.ui.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_list_item.view.*

class RedditNewsListAdapter(
    private val clickListener: (id: String) -> Unit,
    private val imageClickListener: (id: String) -> Unit
) : PagedListAdapter<RedditPost, RedditNewsListAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it, clickListener, imageClickListener) }
    }

    inner class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(
            post: RedditPost,
            clickListener: (id: String) -> Unit,
            imageClickListener: (id: String) -> Unit
        ) {
            //todo video
            with(containerView) {
                tvCaption.text = post.title
                tvChannelName.text = post.subreddit
                //todo format 44k instead 44987, samee for likes
                tvCommentsNumber.text = post.numComments.toString()
                tvLikeCount.text = post.score.toString()
                // todo gif
                ivNewCover.loadImage(post.thumbnail)
                ivNewCover.setOnClickListener { imageClickListener(post.thumbnail) }
                setOnClickListener {
                    clickListener(BASE_URL + post.permalink)
                }
            }
        }

    }
}