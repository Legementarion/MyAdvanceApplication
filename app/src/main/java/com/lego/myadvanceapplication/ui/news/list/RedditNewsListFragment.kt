package com.lego.myadvanceapplication.ui.news.list

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.CustomTabHelper
import com.lego.myadvanceapplication.ui.news.details.RedditDetailsActivity
import kotlinx.android.synthetic.main.reddit_news_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedditNewsListFragment : Fragment() {

    companion object {

        private const val PAGE_EXTRA = "PAGE_EXTRA"

        fun newInstance(page: Page): RedditNewsListFragment {
            val fragment = RedditNewsListFragment()
            fragment.arguments = Bundle().apply { putSerializable(PAGE_EXTRA, page) }
            return fragment
        }
    }

    private val viewModel: RedditNewsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reddit_news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.get(PAGE_EXTRA)?.let { page ->
            viewModel.setPageType(page as Page)
        } ?: return

        val adapter = RedditNewsListAdapter({
            openUrl(it)
        }, {
            val intent = Intent(context, RedditDetailsActivity::class.java)
            intent.putExtra(RedditDetailsActivity.EXTRA_IMG_DETAILS, it)
            startActivity(intent) // todo drawable ?
        })
        feedRv.adapter = adapter

        swipe.setOnRefreshListener {
            viewModel.refresh()  // todo in more correct way
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
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

        viewModel.getPosts().observe(viewLifecycleOwner, Observer {
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
        context?.let {

            val customTabHelper = CustomTabHelper()

            val builder = CustomTabsIntent.Builder()

            // modify toolbar color
            builder.setToolbarColor(ContextCompat.getColor(it, R.color.colorPrimary))

            // add share button to overflow menu
            builder.addDefaultShareMenuItem()

            val anotherCustomTab = CustomTabsIntent.Builder().build()

            val requestCode = 100
            val intent = anotherCustomTab.intent
            intent.data = Uri.parse(url)

            val pendingIntent = PendingIntent.getActivity(
                it,
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
            builder.setStartAnimations(it, android.R.anim.fade_in, android.R.anim.fade_out)
            builder.setExitAnimations(it, android.R.anim.fade_in, android.R.anim.fade_out)

            val customTabsIntent = builder.build()

            // check is chrom available
            val packageName = customTabHelper.getPackageNameToUse(it, url)

            if (packageName == null) {
                // if chrome not available open in web view
                val browserIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
                startActivity(browserIntent)
            } else {
                customTabsIntent.intent.setPackage(packageName)
                customTabsIntent.launchUrl(it, Uri.parse(url))
            }
        }
    }

}