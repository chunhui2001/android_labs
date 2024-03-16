package com.android_labs.bottomsheetdialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.buttonSheet = findViewById(R.id.button)

        this.buttonSheet.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_dialog_layout)

        var editLayout = dialog.findViewById<LinearLayout>(R.id.layoutEdit)
        var shareLayout = dialog.findViewById<LinearLayout>(R.id.layoutShare)
        var uploadLayout = dialog.findViewById<LinearLayout>(R.id.layoutUpload)
        var printLayout = dialog.findViewById<LinearLayout>(R.id.layoutPrint)

        editLayout.setOnClickListener {
            Toast.makeText(this@MainActivity, "Edit clicked", Toast.LENGTH_SHORT).show()
        }

        shareLayout.setOnClickListener {
            Toast.makeText(this@MainActivity, "Share clicked", Toast.LENGTH_SHORT).show()
        }

        uploadLayout.setOnClickListener {
            Toast.makeText(this@MainActivity, "Upload clicked", Toast.LENGTH_SHORT).show()
        }

        printLayout.setOnClickListener {
            Toast.makeText(this@MainActivity, "Print clicked", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}