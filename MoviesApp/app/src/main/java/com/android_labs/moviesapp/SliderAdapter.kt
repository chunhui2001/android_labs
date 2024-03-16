package com.android_labs.moviesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class SliderAdapter(var list: MutableList<SliderItemsModel>, var viewPager: ViewPager2) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {

        private var imageSlide = view.findViewById<ImageView>(R.id.imageSlide)

        fun bind(model: SliderItemsModel) {
            setImg(view, model.image, imageSlide)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_slider, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.list[position])

        if (position == this.list.size - 2) {
            viewPager.post {
                // 循环播放
                list.addAll(list)
                notifyDataSetChanged()
            }
        }
    }

    private fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }

    private fun setImg(view: View, name: String, img: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(60))

        Glide.with(view.context).load(getResourceId(view, name))
            .apply(requestOptions)
            .into(img)
    }
}