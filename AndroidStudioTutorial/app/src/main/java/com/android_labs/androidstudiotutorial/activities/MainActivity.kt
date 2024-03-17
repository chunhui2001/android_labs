package com.android_labs.androidstudiotutorial.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.android_labs.androidstudiotutorial.fragments.AboutFragment
import com.android_labs.androidstudiotutorial.fragments.BuildGpsAppFragment
import com.android_labs.androidstudiotutorial.fragments.CountDownTimerFragment
import com.android_labs.androidstudiotutorial.fragments.ExpandableFloatingActionButtonFragment
import com.android_labs.androidstudiotutorial.fragments.HomeFragment
import com.android_labs.androidstudiotutorial.fragments.ImportantWorkFragment
import com.android_labs.androidstudiotutorial.fragments.KenBurnsViewFragment
import com.android_labs.androidstudiotutorial.fragments.LayoutsFragment
import com.android_labs.androidstudiotutorial.fragments.PersistentBottomSheetFragment
import com.android_labs.androidstudiotutorial.fragments.PicassoImageSliderFragment
import com.android_labs.androidstudiotutorial.fragments.RepeatableWorkFragment
import com.android_labs.androidstudiotutorial.fragments.Resizable9PatchFragment
import com.android_labs.androidstudiotutorial.fragments.TabLayoutFragment
import com.android_labs.androidstudiotutorial.fragments.VisualizerAudioMusicFragment
import com.android_labs.androidstudiotutorial.fragments.WebsocketFragment
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


        navigationView.setNavigationItemSelectedListener {
            this.materialToolBar.title = ""

            if (it.itemId == R.id.miHome) {
                loadFragment(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miAbount) {
                loadFragment(AboutFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miCollapsingToolBar) {
                startActivity(Intent(this@MainActivity, CollapsingToolBarActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miTabLayout) {
                loadFragment(TabLayoutFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.mi9Patch) {
                loadFragment(Resizable9PatchFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miSearchViewToolbar) {
                startActivity(Intent(this@MainActivity, SearchViewToolbarActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miImageSlider) {
                loadFragment(PicassoImageSliderFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miKenBurnsView) {
                loadFragment(KenBurnsViewFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miPersistentBottomSheet) {
                loadFragment(PersistentBottomSheetFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miLayouts) {
                loadFragment(LayoutsFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miFab) {
                loadFragment(ExpandableFloatingActionButtonFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miVisualizerAudio) {
                loadFragment(VisualizerAudioMusicFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miSystemBarColors) {
                startActivity(Intent(this@MainActivity, SystemBarActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miUIController) {
                startActivity(Intent(this@MainActivity, UIControllerActivity::class.java))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miCountDownTimer) {
                loadFragment(CountDownTimerFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miRepeatableWork) {
                loadFragment(RepeatableWorkFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miBuildGpsApp) {
                loadFragment(BuildGpsAppFragment())
                this.materialToolBar.title = "Gps"
                this.materialToolBar.setTitleTextColor(getResources().getColor(R.color.white))
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miImportantWork) {
                loadFragment(ImportantWorkFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.miWebsocket) {
                loadFragment(WebsocketFragment())
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