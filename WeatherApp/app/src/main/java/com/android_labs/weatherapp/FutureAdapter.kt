package com.android_labs.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FutureAdapter(var list: List<Future>): RecyclerView.Adapter<FutureAdapter.ViewHoldder>() {

    inner class ViewHoldder(private var view: View): RecyclerView.ViewHolder(view) {

        private var dayTxt = view.findViewById<TextView>(R.id.dayTxt)
        private var pic = view.findViewById<ImageView>(R.id.pic)
        private var statusTxt = view.findViewById<TextView>(R.id.statusTxt)
        private var lowTxt = view.findViewById<TextView>(R.id.lowTxt)
        private var higtTxt = view.findViewById<TextView>(R.id.highTxt)

        fun bind(item: Future) {
            this.dayTxt.text = item.day
            this.statusTxt.text = item.status
            this.lowTxt.text = item.lowTemp.toString()
            this.higtTxt.text = item.highTemp.toString()

            Glide.with(view.context).load(getResourceId(view, item.picPath)).into(this.pic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldder {
        var inflater = LayoutInflater.from(parent.context).inflate(R.layout.future_viewholder, parent, false)
        return ViewHoldder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHoldder, position: Int) {
        holder.bind(list[position])
    }

    fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }
}