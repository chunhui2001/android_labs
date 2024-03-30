package com.android_labs.videoplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayIconAdapter(private var list: List<IconModel>, private var cb: Callback): RecyclerView.Adapter<PlayIconAdapter.ViewHolder>() {

    interface Callback {
        fun onItemClickListener(item: IconModel)
    }

    fun getList(): MutableList<IconModel> {
        return list.toMutableList()
    }

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var playbackIcon = view.findViewById<ImageView>(R.id.playbackIcon)
        private var iconTitle = view.findViewById<TextView>(R.id.iconTitle)

        private lateinit var currModel: IconModel

        init {
            itemView.setOnClickListener {
                this@PlayIconAdapter.cb.onItemClickListener(this.currModel)
            }
        }

        fun bind(model: IconModel) {
            this.currModel = model

            this.playbackIcon.setImageResource(model.imageView)
            this.iconTitle.text = model.iconTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.video_icon_layout, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}