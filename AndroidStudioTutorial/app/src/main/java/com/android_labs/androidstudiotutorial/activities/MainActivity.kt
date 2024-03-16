package com.android_labs.androidstudiotutorial.activities

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.android_labs.androidstudiotutorial.fragments.AboutFragment
import com.android_labs.androidstudiotutorial.fragments.HomeFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var materialToolBar: MaterialToolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navigationMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        this.materialToolBar = findViewById(R.id.materialToolBar)

        this.materialToolBar.setOnMenuItemClickListener {  menuItem ->
            when (menuItem.itemId) {
                R.id.biHome -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.biAbout -> {
                    loadFragment(AboutFragment())
                    true
                }
                else -> false
            }
        }

        this.drawerLayout = findViewById(R.id.drawerLayout)
        this.navigationView = findViewById(R.id.navigationView)
        this.navigationMenu = this.navigationView.menu

        drawerLayout.setDrawerListener(ActionBarDrawerToggle(
            this@MainActivity, drawerLayout, materialToolBar, R.string.close, R.string.open
        ))

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // 滑动时
                setStatusBarTransparent()

                // 根据 slideOffset 来调整主内容视图的位置
                val contentView = findViewById<View>(R.id.contentLayout) // 替换为你的主内容视图的ID
                contentView.translationX = drawerView.width * slideOffset
            }

            override fun onDrawerOpened(drawerView: View) {
                // 打开时设置状态栏透明
                setStatusBarTransparent()

            }

            override fun onDrawerClosed(drawerView: View) {
                // 关闭时恢复状态栏颜色
                setStatusBarColor(getResources().getColor(R.color.orange))
            }

            override fun onDrawerStateChanged(newState: Int) {
                // 状态变化时
                // setStatusBarTransparent()
            }
        })

        navigationView.setNavigationItemSelectedListener {

            if (it.itemId == R.id.miHome) {
                loadFragment(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            if (it.itemId == R.id.miAbount) {
                loadFragment(AboutFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            true
        }

    }

    private fun loadFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.frameLayout) == null) {
            var ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.frameLayout, fragment)
            ft.commit()
        } else {
            var ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragment)
            ft.commit()
        }
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = getResources().getColor(android.R.color.transparent)
        }
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
}