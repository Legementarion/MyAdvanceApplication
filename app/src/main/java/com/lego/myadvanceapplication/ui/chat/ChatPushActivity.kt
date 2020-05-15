package com.lego.myadvanceapplication.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lego.myadvanceapplication.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatPushActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


    }

}