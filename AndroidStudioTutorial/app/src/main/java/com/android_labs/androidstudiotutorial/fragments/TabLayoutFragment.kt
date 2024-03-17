package com.android_labs.androidstudiotutorial.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android_labs.androidstudiotutorial.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class TabLayoutFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
//    private lateinit var appBarLayout: AppBarLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

//        this.appBarLayout = view.findViewById(R.id.appBarId)
        this.tabLayout = view.findViewById(R.id.tabLayoutId)
        this.viewPager = view.findViewById(R.id.viewPagerId)

        var adapter  = TabViewPagerAdapter(parentFragmentManager)

        adapter.addFragment(Tab1Fragment(), "Tab1")
        adapter.addFragment(Tab2Fragment(), "Tab2")
        adapter.addFragment(Tab3Fragment(), "Tab3")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}