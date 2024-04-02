package com.android_labs.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userNameInput: EditText
    private lateinit var enterRoomBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.userNameInput = findViewById(R.id.userNameInput)
        this.enterRoomBtn = findViewById(R.id.enterRoomBtn)

        this.enterRoomBtn.setOnClickListener { _ ->
            if (this.userNameInput.text.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please input your name", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(ChatActivity.newIntent(this@MainActivity, this.userNameInput.text.toString()))
            }
        }
    }
}