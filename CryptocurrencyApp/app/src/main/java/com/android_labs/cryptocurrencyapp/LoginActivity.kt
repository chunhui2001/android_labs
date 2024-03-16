package com.android_labs.cryptocurrencyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {

    private lateinit var userNameEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var loginButton: AppCompatButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.userNameEdit = findViewById(R.id.user_name_eidt)
        this.passwordEdit = findViewById(R.id.password_edit)
        this.loginButton = findViewById(R.id.login_button)

        this.loginButton.setOnClickListener{ _: View ->
            if (this.userNameEdit.text.trim().isBlank() || this.passwordEdit.text.trim().isBlank()) {
                Toast.makeText(this, "Please fill the login form", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}