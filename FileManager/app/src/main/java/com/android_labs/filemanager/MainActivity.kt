package com.android_labs.filemanager

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var checkPermissionBtn: Button

    private lateinit var createDirBtn: Button
    private lateinit var showPathTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.checkPermissionBtn = findViewById(R.id.checkPermissionBtn)

        this.createDirBtn = findViewById(R.id.createDirBtn)
        this.showPathTv = findViewById(R.id.showPathTv)

        this.checkPermissionBtn.setOnClickListener {

            // Permission storage for sdk 30 or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Toast.makeText(this, "3, " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show()
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

                    Toast.makeText(this, "4, " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show()
                } else {

                    var rootPath = Environment.getExternalStorageDirectory().path

                    Toast.makeText(this, rootPath, Toast.LENGTH_SHORT).show()
                }
            } else {

                // Permission for sdk between 23 and 29
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(this, "1, " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show()
                    if (!this.hasPermission(this, READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, "2, " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show()
                        this.requestPermission(this@MainActivity, READ_EXTERNAL_STORAGE)
                    } else {
                        var rootPath = Environment.getExternalStorageDirectory().path

                        Toast.makeText(this, rootPath, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        this.createDirBtn.setOnClickListener {
            this.showPathTv.text = createDirectory("NewDir").path
        }
    }

    private fun createDirectory(dirName: String): File {
        var f = File(getExternalFilesDir(null)?.absolutePath + "/" + dirName)

        if (!f.exists()) {
            f.mkdir()
        }

        return f
    }

    private fun hasPermission(ctx: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity: Activity, permission: String) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Storage permission is requires, please allow from settings
            Toast.makeText(this, "Storage permission is requires, please allow from settings", Toast.LENGTH_SHORT).show()
            return
        }

        ActivityCompat.requestPermissions(activity, arrayOf(permission), 100)
    }
}