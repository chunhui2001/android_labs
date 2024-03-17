package com.android_labs.androidstudiotutorial.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android_labs.androidstudiotutorial.R


class CollapsingToolBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_tool_bar)

        setSupportActionBar(findViewById(R.id.collapsingToolBarId))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.biHome -> {
                startActivity(Intent(this@CollapsingToolBarActivity, MainActivity::class.java))
                return true
            }
            R.id.biAbout -> {
                // 处理菜单项2的点击事件
                return true
            }
            // 添加更多菜单项的处理逻辑
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

}