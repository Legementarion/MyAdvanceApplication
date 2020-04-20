package com.lego.myadvanceapplication.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyAdvanceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@MyAdvanceApp)
            // modules
            modules(myModule)
        }
    }

    private val myModule = module {
//        single { Controller(get()) }
    }

}