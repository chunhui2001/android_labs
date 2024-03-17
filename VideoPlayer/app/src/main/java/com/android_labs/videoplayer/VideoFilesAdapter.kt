package com.android_labs.videoplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoFilesAdapter(private var videos: List<MediaFiles>): RecyclerView.Adapter<VideoFilesAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
        private var videoMenuMore = view.findViewById<ImageView>(R.id.videoMenuMore)

        private var videoName = view.findViewById<TextView>(R.id.videoName)
        private var videoSize = view.findViewById<TextView>(R.id.videoSize)
        private var videoDuration = view.findViewById<TextView>(R.id.videoDuration)

        init {

        }

        fun bind(model: MediaFiles) {
            this.videoName.text = model.displayName
            this.videoSize.text =model.size
            this.videoDuration.text = model.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.videos[position])
    }
}