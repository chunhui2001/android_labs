package com.android_labs.videoplayer

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.videoplayer.activities.VideoFilesActivity

class VideoFolderAdapter(private var context: Context, private var folders: List<String>): RecyclerView.Adapter<VideoFolderAdapter.ViewHolder>() {

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
            this.fileCounts.text = "${countOfVideos(this.folderName.text.toString())} Videos"
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

    private fun countOfVideos(folderName: String): Int {

        var videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var cursor = context.contentResolver.query(videoUri, null, null, null, null)

        var count = 0

        if (cursor != null && cursor.moveToNext()) {

            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA).toInt())!!

                var idx = path.lastIndexOf("/")
                var subString = path.subSequence(0, idx).toString()

                if (subString.endsWith(folderName)) {
                    count ++
                }

            } while (cursor.moveToNext())
        }

        return count
    }
}