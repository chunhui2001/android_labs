package com.android_labs.travelapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

class PopularAdapter(var list: List<City>) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var popularImg = view.findViewById<ImageView>(R.id.popularImg)
        private var popularTitleTxt = view.findViewById<TextView>(R.id.popularTitleTxt)
        private var locationTxt = view.findViewById<TextView>(R.id.locationTxt)
        private var scoreTxt = view.findViewById<TextView>(R.id.scoreTxt)

        private lateinit var city: City

        init {
            view.setOnClickListener {
                var intent = Intent(view.context, DetailsActivity::class.java)
                intent.putExtra("city", city)
                view.context.startActivity(intent)
            }
        }

        fun bind(city: City) {
            this.city = city

            this.popularTitleTxt.text = city.title
            this.locationTxt.text = city.location
            this.scoreTxt.text = city.score.toString()

            Glide.with(view.context)
                 .load(getResourceId(view, city.pic))
                 .transform(CenterCrop(), GranularRoundedCorners(40.0f, 40.0f, 40.0f, 40.0f))
                 .into(this.popularImg)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_popular, parent, false)
        return ViewHolder(view)
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