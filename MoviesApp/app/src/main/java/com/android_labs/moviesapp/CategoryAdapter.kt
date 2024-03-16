package com.android_labs.moviesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.moviesapp.models.Datum
import com.android_labs.moviesapp.models.GenresModel
import com.android_labs.moviesapp.models.ListFilm
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class CategoryAdapter(var list: List<GenresModel>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {

        private var categoryName: TextView = view.findViewById(R.id.categoryNameTxt)

        fun bind(model: GenresModel){
            this.categoryName.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.list[position])
    }

    private fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }

    private fun setImg(view: View, url: String, img: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(30))

        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(img)
    }
}