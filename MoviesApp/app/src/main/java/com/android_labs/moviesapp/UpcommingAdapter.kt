package com.android_labs.moviesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.moviesapp.models.Datum
import com.android_labs.moviesapp.models.ListFilm
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class UpcommingAdapter(private var list: ListFilm, var callbacks: BestMovieAdapter.Callbacks): RecyclerView.Adapter<UpcommingAdapter.ViewHolder>() {

    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {

        private var filmTitle: TextView = view.findViewById(R.id.filmTitleTxt)
        private var filmPic: ImageView = view.findViewById(R.id.filmPicImg)
        private var container: ConstraintLayout = view.findViewById(R.id.container)

        private lateinit var model: Datum;

        fun bind(model: Datum){
            this.model = model
            this.filmTitle.text = model.title

            setImg(view, model.poster, this.filmPic)

            this.container.setOnClickListener {
                callbacks.onFilmSelected(this.model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_best_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return list.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.list.data[position])
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