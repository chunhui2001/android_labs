package com.android_labs.androidstudiotutorial.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private var fragmentList = emptyList<Fragment>().toMutableList()
    private var fragmentTitles = emptyList<String>().toMutableList()


    override fun getCount(): Int {
        return fragmentTitles.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitles.add(title)
    }
}