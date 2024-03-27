package com.android_labs.videoplayer.activities

import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android_labs.videoplayer.MediaFiles
import com.android_labs.videoplayer.R
import com.android_labs.videoplayer.VideoFilesAdapter
import java.io.File

class VideoFilesActivity : AppCompatActivity() {

    private lateinit var videoListView: RecyclerView

    private lateinit var reloadVideoList: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video_files)

        this.reloadVideoList = findViewById(R.id.reloadVideoList)
        this.videoListView = findViewById(R.id.videoListView)
        this.videoListView.layoutManager = LinearLayoutManager(this)

        val folderName = intent.getStringExtra("folderName")!!

        supportActionBar?.title = File(folderName).name

        this.videoListView.adapter = VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName).toMutableList())

        this.reloadVideoList.setOnRefreshListener {
            this.videoListView.adapter = VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName).toMutableList())
            this.reloadVideoList.isRefreshing = false
        }
    }

    private fun listVideos(folderName: String): List<MediaFiles> {

        var listVideos = mutableListOf<MediaFiles>()

        var videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Video.Media.DATA + " like?"
        var selectionArg = arrayOf("%$folderName%")

        var cursor = contentResolver.query(videoUri, null, selection, selectionArg, null)

        if (cursor != null && cursor.moveToNext()) {

            do {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID).toInt())
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE).toInt())
                val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME).toInt())
                val size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE).toInt())
                val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION).toInt())
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA).toInt())
                val dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED).toInt())

                listVideos.add(
                    MediaFiles(
                        id,title,
                        displayName,
                        size, duration, path, dateAdded
                    ))
            } while (cursor.moveToNext())
        }

        return listVideos
    }

}