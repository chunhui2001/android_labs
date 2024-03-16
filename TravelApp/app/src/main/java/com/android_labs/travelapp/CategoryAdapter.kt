package com.android_labs.travelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

class CategoryAdapter(private var list: List<Category>): RecyclerView.Adapter<CategoryAdapter.ViewHoldder>() {

    inner class ViewHoldder(private var view: View): RecyclerView.ViewHolder(view) {

        private var cateTitleTxt = view.findViewById<TextView>(R.id.cateTitleTxt)
        private var cateImg = view.findViewById<ImageView>(R.id.cateImg)

        fun bind(item: Category) {
            this.cateTitleTxt.text = item.title
            Glide.with(view.context)
                 .load(getResourceId(view, item.pic))
                 .transform(CenterCrop(), GranularRoundedCorners(40.0f, 40.0f, 40.0f, 40.0f))
                 .into(this.cateImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHoldder(view)
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