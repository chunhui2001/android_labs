package com.android_labs.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android_labs.todoapp.R
import com.android_labs.todoapp.services.UserService
import com.android_labs.todoapp.services.UtilService
import com.android_labs.todoapp.utils.SharedPreference
import com.android_labs.todoapp.widget.ProgressButton

class LoginActivity : AppCompatActivity() {

    private val utilService: UtilService = UtilService()
    private lateinit var userService: UserService

    private lateinit var loginBtn: View
    private lateinit var goRegisterBtn: Button

    private lateinit var usernameTxt: EditText
    private lateinit var passwdTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.userService = UserService(this@LoginActivity)

        this.loginBtn = findViewById(R.id.loginBtn)
        this.usernameTxt = findViewById(R.id.usernameTxt)
        this.passwdTxt = findViewById(R.id.passwdTxt)
        this.goRegisterBtn = findViewById(R.id.goRegisterBtn)

        var loginBtnText = loginBtn.findViewById<TextView>(R.id.textView)
        loginBtnText.text = "Login"

        this.goRegisterBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        this.loginBtn.setOnClickListener {
            utilService.hideKeyboard(it, this@LoginActivity)

            var progressButton = ProgressButton(this@LoginActivity, loginBtn)

            val username = this.usernameTxt.text.toString().trim();
            val passwd = this.passwdTxt.text.toString().trim();

            if (TextUtils.isEmpty(username)) {
                utilService.showSnackBar(it, "请输入用户名!")
            } else if (TextUtils.isEmpty(passwd)) {
                utilService.showSnackBar(it, "请输入密码!")
            } else {
                progressButton.buttonActivited()

                userService.accessToken<String>(username, passwd) { result ->
                    progressButton.buttonFinished()

                    if (!result.isSuccess) {
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    } else {
                        userService.saveAccessToken(result.data)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}