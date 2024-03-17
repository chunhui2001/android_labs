package com.android_labs.androidstudiotutorial.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android_labs.androidstudiotutorial.R
import com.squareup.picasso.Picasso

class PicassoImageSliderFragment: Fragment() {

    private val images = listOf("hero", "unnamed", "slide1", "slide3", "slide4", "ss_slide")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_image_view_pager, container, false)

        var adapter = ViewPagerAdapter(requireContext(), images)

        var viewPager = view.findViewById<ViewPager>(R.id.viewPager)

        viewPager.adapter = adapter

        return view
    }

    inner class ViewPagerAdapter(private var ctx: Context, private var images: List<String>): PagerAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var imageImage = ImageView(ctx)

            Picasso.get().load(getResourceId(ctx, images[position])).fit().centerCrop().into(imageImage)
            container.addView(imageImage)

            return imageImage
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        private fun getResourceId(ctx: Context, name: String): Int {
            return ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
        }
    }
}