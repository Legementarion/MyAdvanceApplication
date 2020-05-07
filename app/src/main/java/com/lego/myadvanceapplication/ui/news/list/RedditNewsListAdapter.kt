package com.lego.myadvanceapplication.ui.news.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.domain.news.model.RedditPost
import kotlinx.android.extensions.LayoutContainer

class RedditNewsListAdapter(
    private val clickListener: (id: Long) -> Unit
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
        getItem(position)?.let { holder.bindData(it, clickListener) }
    }

    inner class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(
            post: RedditPost,
            clickListener: (id: Long) -> Unit
        ) {
            containerView.setOnClickListener {
                clickListener(post.id)
            }
        }

    }
}