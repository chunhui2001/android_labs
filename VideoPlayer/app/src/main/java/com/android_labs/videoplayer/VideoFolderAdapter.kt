package com.android_labs.videoplayer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.videoplayer.activities.VideoFilesActivity

class VideoFolderAdapter(private var folders: List<String>): RecyclerView.Adapter<VideoFolderAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var folderName = view.findViewById<TextView>(R.id.folderName)
        private var folderPath = view.findViewById<TextView>(R.id.folderPath)
        private var fileCounts = view.findViewById<TextView>(R.id.fileCounts)

        private lateinit var currFolder: String

        init {
            this.view.setOnClickListener {
                view.context.startActivity(
                    Intent(view.context, VideoFilesActivity::class.java)
                        .putExtra("folderName", this.currFolder)
                )
            }
        }

        fun bind(model: String) {

            this.currFolder = model

            var idx = model.lastIndexOf("/")
            this.folderName.text = model.substring(idx + 1)
            this.folderPath.text =model
            this.fileCounts.text = "5 Videos"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.folders_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.folders[position])
    }
}