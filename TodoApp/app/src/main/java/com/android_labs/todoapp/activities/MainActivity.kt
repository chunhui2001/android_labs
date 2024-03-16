package com.android_labs.todoapp.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import com.android_labs.todoapp.R
import com.android_labs.todoapp.adapters.TodoAdapter
import com.android_labs.todoapp.fragments.HomeFragment
import com.android_labs.todoapp.fragments.RagintFragment
import com.android_labs.todoapp.models.TodoModel
import com.android_labs.todoapp.services.API_URL_USER_DEFAULT_IMG
import com.android_labs.todoapp.services.API_URL_USER_HEAD_IMG
import com.android_labs.todoapp.services.UserService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import java.time.Instant


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var materialToolBar: MaterialToolbar
    private lateinit var navigationView: NavigationView
    private lateinit var usernameTxt: TextView
    private lateinit var userHeadImg: ImageView

    private lateinit var userService: UserService
    private lateinit var navigationMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val now = Instant.now().toEpochMilli();

        // 设置状态栏颜色
        setStatusBarColor(getResources().getColor(R.color.orange));

        this.userService = UserService(this@MainActivity)

        this.drawerLayout = findViewById(R.id.drawerLayout)
        this.materialToolBar = findViewById(R.id.materialToolBar)
        this.navigationView = findViewById(R.id.navigationView)

        var toggle = ActionBarDrawerToggle(
            this@MainActivity, drawerLayout, materialToolBar, R.string.close, R.string.open
        )

        this.usernameTxt = this.navigationView.getHeaderView(0).findViewById(R.id.usernameTxt);
        this.userHeadImg = this.navigationView.getHeaderView(0).findViewById(R.id.userHeadImg);

        this.navigationMenu = this.navigationView.menu
        this.navigationMenu.findItem(R.id.miBus).setVisible(false)

        this.materialToolBar.setOnMenuItemClickListener {  menuItem ->
            when (menuItem.itemId) {
                R.id.biReloading -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.biHome -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.biShare -> {
                    startActivity(
                        Intent.createChooser(Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, "dddd"), "Share it")
                    )
                    true
                }
                else -> false
            }
        }

        val ctx = this;

        drawerLayout.addDrawerListener(object : DrawerListener {
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

                userService.userInfo { userInfo ->
                    if (userInfo == null) {
                        navigationMenu.findItem(R.id.miLogin).setVisible(true)
                        navigationMenu.findItem(R.id.miLogout).setVisible(false)
                        setImg(ctx, API_URL_USER_DEFAULT_IMG, userHeadImg)
                    } else {
                        navigationMenu.findItem(R.id.miLogin).setVisible(false)
                        navigationMenu.findItem(R.id.miLogout).setVisible(true)
                        usernameTxt.text = userInfo.username

                        userInfo.resourceId?.let {

                            setImg(
                                ctx,
                                "$API_URL_USER_HEAD_IMG/$it?v=$now",
                                userHeadImg
                            )
                        }
                    }
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                // 关闭时恢复状态栏颜色
                setStatusBarColor(getResources().getColor(R.color.orange))
            }

            override fun onDrawerStateChanged(newState: Int) {
                // 状态变化时
//                setStatusBarTransparent()
            }
        })

        drawerLayout.setDrawerListener(toggle)

        navigationView.setNavigationItemSelectedListener {

            if (it.itemId == R.id.miHome) {
                loadFragment(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miBus) {
//                loadFragment(BusFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miRating) {
                loadFragment(RagintFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
//                finish()
            } else if (it.itemId == R.id.miLogin) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miLogout) {
                userService.logout()
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
                finish()
            }

            true
        }

        loadFragment(HomeFragment())
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

    private fun setImg(ctx: Context, url: String, img: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(300))

        Glide.with(ctx)
            .load(url)
            .apply(requestOptions)
            .into(img)
    }
}