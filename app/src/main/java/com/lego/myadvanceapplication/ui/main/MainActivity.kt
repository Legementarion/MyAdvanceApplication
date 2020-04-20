package com.lego.myadvanceapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.base.navigateToSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelBasic: BasicMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelBasic = BasicMainViewModel()

        settingsBtn.setOnClickListener {
            navigateToSettings()
        }

    }

}
