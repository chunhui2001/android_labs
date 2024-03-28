package com.android_labs.videoplayer.activities

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
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

    private var listVideos = mutableListOf<MediaFiles>()

    private lateinit var folderName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video_files)

        this.reloadVideoList = findViewById(R.id.reloadVideoList)
        this.videoListView = findViewById(R.id.videoListView)
        this.videoListView.layoutManager = LinearLayoutManager(this)
        this.folderName = intent.getStringExtra("folderName")!!

        supportActionBar?.title = File(folderName).name

        this.videoListView.adapter = VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName, null).toMutableList())

        this.reloadVideoList.setOnRefreshListener {
            this.videoListView.adapter = VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName, null).toMutableList())
            this.reloadVideoList.isRefreshing = false
        }
    }

    private fun listVideos(folderName: String, filter: String?): List<MediaFiles> {

        var sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE)
        var sortBy = sharedPreferences.getString("sortBy", "")

        listVideos.clear()

        var videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        var orderBy = when (sortBy) {
            "Name" -> {
                MediaStore.MediaColumns.DISPLAY_NAME + " ASC"
            }
            "Size" -> {
                MediaStore.MediaColumns.SIZE + " DESC"
            }
            "Date" -> {
                MediaStore.MediaColumns.DATE_ADDED + " DESC"
            }
            "Duration" -> {
                MediaStore.MediaColumns.DURATION + " DESC"
            } else -> {
                MediaStore.MediaColumns.DATE_ADDED + " DESC"
            }
        }

        val selection = MediaStore.Video.Media.DATA + " like?"
        var selectionArg = arrayOf("%$folderName%")

        var cursor = contentResolver.query(videoUri, null, selection, selectionArg, orderBy)

        if (cursor != null && cursor.moveToNext()) {

            do {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID).toInt())
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE).toInt())
                val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME).toInt())
                val size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE).toInt())
                val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION).toInt())
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA).toInt())
                val dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED).toInt())

                if (filter == null || title.lowercase().contains(filter!!)) {
                    listVideos.add(
                        MediaFiles(
                            id,title,
                            displayName,
                            size, duration, path, dateAdded
                        ))
                }
            } while (cursor.moveToNext())
        }

        return listVideos
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.video_menu, menu)

        var menuItem = menu?.findItem(R.id.miSearchView)
        var miRefresh = menu?.findItem(R.id.miRefresh)

        var searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var input = newText?.lowercase()!!

                this@VideoFilesActivity.videoListView.adapter =
                    VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName, input).toMutableList())

                return true
            }
        })

        miRefresh?.setOnMenuItemClickListener {
            this@VideoFilesActivity.reloadVideoList.isRefreshing = true

            this@VideoFilesActivity.videoListView.adapter =
                VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName, null).toMutableList())

            Handler().postDelayed( {
                this@VideoFilesActivity.reloadVideoList.isRefreshing = false
            }, 1000)

            true
        }

//        val params = searchView.layoutParams as LinearLayout.LayoutParams
//        val defaultMarginStart = params.marginStart
//
//        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
//            supportActionBar?.setDisplayShowTitleEnabled(!hasFocus)
//
//            if (hasFocus) {
//                params.marginStart = 0
//            } else {
//                params.marginStart = defaultMarginStart
//            }
//        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        when (item.itemId) {
            R.id.miSortBy -> {
                var alertDialog = AlertDialog.Builder(this@VideoFilesActivity)

                alertDialog.setTitle("Sort By")

                alertDialog.setSingleChoiceItems(
                    arrayOf("Name (A to Z)", "Size (Big to Small)", "Date (New to Old)", "Duration (Long to Short)"),
                    -1
                ) { _, which ->
                    when (which) {
                        0 -> {
                            editor.putString("sortBy", "Name")
                        }
                        1 -> {
                            editor.putString("sortBy", "Size")
                        }
                        2 -> {
                            editor.putString("sortBy", "Date")
                        }
                        3 -> {
                            editor.putString("sortBy", "Duration")
                        }
                    }
                }

                alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

                alertDialog.setPositiveButton("Ok"
                ) { _, _ ->
                    editor.apply()
                    this.videoListView.adapter = VideoFilesAdapter(this@VideoFilesActivity, listVideos(folderName, null).toMutableList())
                }

                alertDialog.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}