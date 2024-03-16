package com.android_labs.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HourlyAdapter(val list: List<Hourly>): RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        val hourTxt = view.findViewById<TextView>(R.id.hourTxt)
        val picImage = view.findViewById<ImageView>(R.id.pic)
        val tmpTxt = view.findViewById<TextView>(R.id.tmpTxt)

        fun bind(item: Hourly) {
            this.hourTxt.text = item.hour
            this.tmpTxt.text = item.temp.toString()

            Glide.with(view.context).load(getResourceId(view, item.picPath)).into(picImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(parent.context).inflate(R.layout.hourly_viewholder, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }
}