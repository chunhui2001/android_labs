package com.android_labs.cryptocurrencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.bottom_nav_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.bottom_nav_container, BottomNavFragment.newInstance()).commit()
        }

        if (supportFragmentManager.findFragmentById(R.id.user_info_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.user_info_container, UserInfoFragment.newInstance()).commit()
        }

        if (supportFragmentManager.findFragmentById(R.id.balance_list_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.balance_list_container, BalanceListFragment.newInstance()).commit()
        }
    }
}