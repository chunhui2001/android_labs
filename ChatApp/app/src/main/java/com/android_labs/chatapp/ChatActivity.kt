package com.android_labs.chatapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, userName: String): Intent {
            return Intent(context, ChatActivity::class.java).putExtra("userName", userName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}