package com.lego.myadvanceapplication.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lego.myadvanceapplication.ui.base.navigateToMain

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToMain()
    }
}