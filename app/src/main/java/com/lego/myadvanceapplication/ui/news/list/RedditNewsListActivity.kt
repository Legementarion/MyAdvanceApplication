package com.lego.myadvanceapplication.ui.news.list

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.CustomTabHelper
import com.lego.myadvanceapplication.ui.news.details.RedditDetailsActivity
import com.lego.myadvanceapplication.ui.news.details.RedditDetailsActivity.Companion.EXTRA_IMG_DETAILS
import kotlinx.android.synthetic.main.activity_reddit_news_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedditNewsListActivity : AppCompatActivity() {

    private val viewModel: RedditNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_news_list)

        val adapter = RedditNewsListAdapter({
            openUrl(it)
        }, {
            val intent = Intent(baseContext, RedditDetailsActivity::class.java)
            intent.putExtra(EXTRA_IMG_DETAILS, it)
            startActivity(intent) // todo drawable ?
        })
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

    private fun openUrl(url: String) {
        val customTabHelper = CustomTabHelper()

        val builder = CustomTabsIntent.Builder()

        // modify toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))

        // add share button to overflow menu
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.data = Uri.parse(url)

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // add menu item to oveflow
        builder.addMenuItem("Sample item", pendingIntent)

        // menu item icon
        // val bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
        // builder.setActionButton(bitmap, "Android", pendingIntent, true)

        // modify back button icon
        // builder.setCloseButtonIcon(bitmap)

        // show website title
        builder.setShowTitle(true)

        // animation for enter and exit of tab
        builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
        builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)

        val customTabsIntent = builder.build()

        // check is chrom available
        val packageName = customTabHelper.getPackageNameToUse(this, url)

        if (packageName == null) {
            // if chrome not available open in web view
            val browserIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            startActivity(browserIntent)
        } else {
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
    }
}
