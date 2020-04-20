package com.lego.myadvanceapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lego.myadvanceapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelBasic: BasicMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelBasic = BasicMainViewModel()

    }

    fun onSettingsButtonClicked(v: View) {
//        viewModelBasic.deleteUser(selectedUser)
    }

}
