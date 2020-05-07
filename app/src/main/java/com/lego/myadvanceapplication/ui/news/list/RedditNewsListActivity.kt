package com.lego.myadvanceapplication.ui.news.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.lego.myadvanceapplication.R
import kotlinx.android.synthetic.main.activity_reddit_news_list.*

class RedditNewsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_news_list)

        Handler().postDelayed({
            progressBar.visibility = View.GONE
            emptyViewGroup.visibility = View.VISIBLE
            feedRv.visibility = View.GONE
        }, 1200)

    }
}
