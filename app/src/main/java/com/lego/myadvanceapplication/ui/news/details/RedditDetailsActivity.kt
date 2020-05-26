package com.lego.myadvanceapplication.ui.news.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.utils.loadImage
import kotlinx.android.synthetic.main.activity_reddit_details.*

class RedditDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_details)
        intent.getStringExtra(EXTRA_IMG_DETAILS)?.let { imgUrl ->
            ivDetails.loadImage(imgUrl)
        } ?: finish()
    }

    companion object {
        const val EXTRA_IMG_DETAILS = "EXTRA_IMG_DETAILS"
    }

}