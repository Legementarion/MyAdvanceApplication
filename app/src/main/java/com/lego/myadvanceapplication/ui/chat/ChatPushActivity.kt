package com.lego.myadvanceapplication.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.actions.NoteIntents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.base.navigateToSignIn
import kotlinx.android.synthetic.main.activity_chat.*

class ChatPushActivity : AppCompatActivity() {

    companion object {
        const val PAGE_COUNT = 2
    }

    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent = intent
        if (NoteIntents.ACTION_CREATE_NOTE == intent.action) {
            if (intent.hasExtra(NoteIntents.EXTRA_TEXT)) {

            }

//            AppIndex.AppIndexApi.end(mClient, setAlarmAction)
        }

        // Check Sign status
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Sign In activity
            navigateToSignIn()
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
        viewPager.offscreenPageLimit = 0
        tabs.setupWithViewPager(viewPager)

    }

    private class ChatPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                ChatType.Public.type -> ChatTodoFragment.newInstance(ChatType.Public)
                else -> ChatTodoFragment.newInstance(ChatType.My)
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                ChatType.Public.type -> "Public"
                else -> "Only My"
            }
        }

        override fun getCount(): Int {
            return PAGE_COUNT
        }
    }

}

enum class ChatType(val type: Int) {
    Public(0),
    My(1)
}