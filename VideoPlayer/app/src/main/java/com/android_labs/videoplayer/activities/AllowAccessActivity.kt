package com.android_labs.videoplayer.activities

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android_labs.videoplayer.R
import java.lang.Exception

private const val STORAGE_PERMISSION_REQUEST_CODE = 1
private const val REQUEST_PERMISSION_SETTINGS_CODE = 12

class AllowAccessActivity : AppCompatActivity() {

    private lateinit var allowAccessBtn: AppCompatButton

    override fun onRestart() {
        super.onRestart()
        if (hasStoragePermission()) {
            goToMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_access)

        setStatusBarColor(getResources().getColor(R.color.allow_color))

        this.allowAccessBtn = findViewById(R.id.allowAccessBtn)

        if (hasStoragePermission()) {
            goToMainActivity()
        } else {
            this.allowAccessBtn.setOnClickListener {

                if (!hasStoragePermission()) {
                    requestStoragePermission()
                } else {
                    goToMainActivity()
                }
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(
            Intent(this@AllowAccessActivity, MainActivity::class.java)
                .putExtra("rootPath", Environment.getExternalStorageDirectory().path)
        )
        finish()
    }

    private fun hasStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                return false
            } else {
                return true
            }
        } else {
            // Permission for sdk between 23 and 29
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return this.hasPermission(this, WRITE_EXTERNAL_STORAGE)
            }

            return false
        }
    }

    private fun requestStoragePermission() {
        // Permission storage for sdk 30 or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.setData(Uri.parse(String.format("package:%s", applicationContext.packageName)))
                    startActivityIfNeeded(intent, 101)
                } catch (e: Exception) {
                    var intent = Intent()
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    startActivityIfNeeded(intent, 101)
                }
            } else {
                // already have permission
            }
        } else {
            // Permission for sdk between 23 and 29
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!this.hasPermission(this, WRITE_EXTERNAL_STORAGE)) {
                    this.requestPermission(this@AllowAccessActivity, WRITE_EXTERNAL_STORAGE)
                } else {
                    // already have permission
                }
            }
        }
    }

    private fun hasPermission(activity: Activity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private fun requestPermission(activity: Activity, permission: String) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "Storage permission is requires, please allow form settings", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "8888", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                100
            )
        }
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode != STORAGE_PERMISSION_REQUEST_CODE) {
//            return
//        }
//
//        if (hasPermission(permissions[0])) {
//            startActivity(Intent(this@AllowAccessActivity, MainActivity::class.java))
//            finish()
//            return
//        }
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this@AllowAccessActivity, permissions[0])) {
//            requestPermission(WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_REQUEST_CODE)
//            return
//        }
//
//        // user clicked on never asked again
//        AlertDialog
//            .Builder(this@AllowAccessActivity)
//            .setTitle("App Permission")
//            .setMessage(R.string.text2)
//            .setPositiveButton("Open Settings") { _, _ ->
//                // 在这里处理 "Open Settings" 按钮的点击事件
//                startActivityForResult(
//                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
//                        Uri.fromParts(
//                            "package",
//                            packageName,
//                            null
//                        )
//                    ), REQUEST_PERMISSION_SETTINGS_CODE
//                )
//            }
//            .create()
//            .show()
//    }
}