package com.lego.myadvanceapplication.ui.news.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lego.myadvanceapplication.R
import kotlinx.android.synthetic.main.activity_reddit_news_list.*

class RedditNewsListActivity : AppCompatActivity() {

    companion object {
        const val PAGE_COUNT = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_news_list)

        val pagerAdapter = RedditPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = PAGE_COUNT

        tabs.setupWithViewPager(viewPager)

        val imageResId = intArrayOf(  //todo change this icons
            R.drawable.ic_baseline_whatshot_24,
            R.drawable.ic_top,
            R.drawable.ic_star_new,
            R.drawable.ic_save
        )

        for (i in imageResId.indices) {
            tabs.getTabAt(i)?.setIcon(imageResId[i])
        }
    }

    private class RedditPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                Page.HOT.pos -> RedditNewsListFragment.newInstance(Page.HOT)
                Page.TOP.pos -> RedditNewsListFragment.newInstance(Page.TOP)
                Page.NEW.pos -> RedditNewsListFragment.newInstance(Page.NEW)
                else -> RedditNewsListFragment.newInstance(Page.FAVORITE)
            }
        }

        override fun getCount(): Int {
            return PAGE_COUNT
        }
    }

}

enum class Page(val pos: Int) {
    HOT(0),
    TOP(1),
    NEW(2),
    FAVORITE(3);
}
