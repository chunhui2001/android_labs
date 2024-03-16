package com.android_labs.bottomnavigationdrawertoolbarfragment

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var materialToolBar: MaterialToolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var usernameTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 设置状态栏颜色
//        setStatusBarColor(getResources().getColor(R.color.orange));

        this.drawerLayout = findViewById(R.id.drawerLayout)
        this.materialToolBar = findViewById(R.id.materialToolBar)
        this.frameLayout = findViewById(R.id.frameLayout)
        this.navigationView = findViewById(R.id.navigationView)

        this.headerView = this.navigationView.getHeaderView(0)
        this.usernameTxt = this.headerView.findViewById(R.id.usernameTxt)

        this.usernameTxt.text = "Facebook"

        var toggle = ActionBarDrawerToggle(
            this@MainActivity, drawerLayout, materialToolBar, R.string.close, R.string.open
        )

        drawerLayout.addDrawerListener(toggle)

        navigationView.setNavigationItemSelectedListener {

            if (it.itemId == R.id.miHome) {
                loadFragment(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miBus) {
                loadFragment(BusFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            true
        }

        materialToolBar.setOnMenuItemClickListener {

            if (it.itemId == R.id.biHome) {
                loadFragment(HomeFragment())
            } else if (it.itemId == R.id.biNotification) {

                Toast.makeText(this@MainActivity, "Notification", Toast.LENGTH_SHORT).show()
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

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
}