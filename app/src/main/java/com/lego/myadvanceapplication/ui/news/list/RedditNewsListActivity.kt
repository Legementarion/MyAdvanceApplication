package com.lego.myadvanceapplication.ui.news.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lego.myadvanceapplication.R
import kotlinx.android.synthetic.main.activity_reddit_news_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedditNewsListActivity : AppCompatActivity() {

    private val viewModel: RedditNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_news_list)

        val adapter = RedditNewsListAdapter {
            viewModel.openNewsDetails(it)
        }
        feedRv.adapter = adapter

        swipe.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.getState().observe(this, Observer {
            when (it) {
                RedditNewsDataSource.State.LOADING -> {
                    showLoadingState()
                }
                RedditNewsDataSource.State.DONE -> {
                    hideLoadingState()
                }
                else -> {
                    showEmptyState()
                }
            }
        })

        viewModel.getPosts().observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun showLoadingState() {
        progressBar.visibility = View.VISIBLE
        emptyViewGroup.visibility = View.GONE
        feedRv.visibility = View.GONE
    }

    private fun hideLoadingState() {
        progressBar.visibility = View.GONE
        emptyViewGroup.visibility = View.GONE
        feedRv.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        progressBar.visibility = View.GONE
        emptyViewGroup.visibility = View.VISIBLE
        feedRv.visibility = View.GONE
    }
}
