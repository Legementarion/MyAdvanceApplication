package com.lego.myadvanceapplication.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_chat.*

class ChatPushActivity : AppCompatActivity() {

    companion object {
        const val PAGE_COUNT = 2
    }

    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Check Sign status
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        val pagerAdapter = ChatPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = PAGE_COUNT

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        tabs.setupWithViewPager(viewPager)

    }

    private class ChatPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> ChatTodoFragment.newInstance(ChatType.Public)
                else -> ChatTodoFragment.newInstance(ChatType.My)
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "Public"
                else -> "Only My"
            }
        }

        override fun getCount(): Int {
            return PAGE_COUNT
        }
    }

}

enum class ChatType {
    Public,
    My
}