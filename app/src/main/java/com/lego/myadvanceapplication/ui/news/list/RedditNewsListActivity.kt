package com.lego.myadvanceapplication.ui.news.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
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

        viewModel.loadData()


        Handler().postDelayed({
            progressBar.visibility = View.GONE
            emptyViewGroup.visibility = View.VISIBLE
            feedRv.visibility = View.GONE
        }, 1200)

    }
}
