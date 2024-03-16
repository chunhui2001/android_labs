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
import com.android_labs.todoapp.widget.ProgressButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var goLoginBtn: Button
    private lateinit var registerBtn: View

    private lateinit var usernameTxt: EditText
    private lateinit var emailTxt: EditText
    private lateinit var passwdTxt: EditText

    private val utilService: UtilService = UtilService()
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.userService = UserService(this@RegisterActivity)

        this.registerBtn = findViewById(R.id.registerBtn)
        this.goLoginBtn = findViewById(R.id.goLoginBtn)

        this.usernameTxt = findViewById(R.id.usernameTxt)
        this.emailTxt = findViewById(R.id.emailTxt)
        this.passwdTxt = findViewById(R.id.passwdTxt)

        var loginBtnText = registerBtn.findViewById<TextView>(R.id.textView)
        loginBtnText.text = "Register now"

        this.goLoginBtn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        this.registerBtn.setOnClickListener {
            utilService.hideKeyboard(it, this@RegisterActivity)

            var progressButton = ProgressButton(this@RegisterActivity, registerBtn)

            val username = this.usernameTxt.text.toString().trim();
            val email = this.emailTxt.text.toString().trim();
            val passwd = this.passwdTxt.text.toString().trim();

            if (TextUtils.isEmpty(username)) {
                utilService.showSnackBar(it, "请输入用户名!")
            } else if (TextUtils.isEmpty(email)) {
                utilService.showSnackBar(it, "请输入邮箱!")
            } else if (TextUtils.isEmpty(passwd)) {
                utilService.showSnackBar(it, "请输入密码!")
            } else {
                progressButton.buttonActivited()

                userService.signup(username, passwd, email) { result ->
                    progressButton.buttonFinished()

                    if (!result.isSuccess) {
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }
                }
            }
        }
    }
}