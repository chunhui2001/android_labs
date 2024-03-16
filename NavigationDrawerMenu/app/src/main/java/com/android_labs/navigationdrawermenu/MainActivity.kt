package com.android_labs.navigationdrawermenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var buttonDrawerToggle: ImageButton
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.drawerLayout = findViewById(R.id.drawerLayout)
        this.buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle)
        this.navigationView = findViewById(R.id.navigationView)

        this.buttonDrawerToggle.setOnClickListener {
            drawerLayout.open()
        }

        var headerView = navigationView.getHeaderView(0)
        var useriamge = headerView.findViewById<ImageView>(R.id.userimage)
        var username = headerView.findViewById<TextView>(R.id.username)

        this.navigationView.setNavigationItemSelectedListener {
            val itemId = it.itemId

            if (itemId == R.id.navMenu) {
                Toast.makeText(this@MainActivity, "Menu Clicked", Toast.LENGTH_SHORT).show()
            }

            if (itemId == R.id.navCart) {
                Toast.makeText(this@MainActivity, "Cart Clicked", Toast.LENGTH_SHORT).show()
            }

            if (itemId == R.id.navFavourite) {
                Toast.makeText(this@MainActivity, "Favourite Clicked", Toast.LENGTH_SHORT).show()
            }

            if (itemId == R.id.navOrders) {
                Toast.makeText(this@MainActivity, "Orders Clicked", Toast.LENGTH_SHORT).show()
            }

            if (itemId == R.id.navHistory) {
                Toast.makeText(this@MainActivity, "History Clicked", Toast.LENGTH_SHORT).show()
            }

            drawerLayout.close()

            true
        }
    }
}