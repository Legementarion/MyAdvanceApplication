package com.lego.myadvanceapplication.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.core.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyAdvanceApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@MyAdvanceApp)
            // modules
            modules(baseModule)
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        when (preferences.getBoolean(getString(R.string.pref_key_night), false)) {
            true -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

}