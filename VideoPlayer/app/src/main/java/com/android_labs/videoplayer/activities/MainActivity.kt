package com.android_labs.videoplayer.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android_labs.videoplayer.R
import com.android_labs.videoplayer.VideoFolderAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var folderListView: RecyclerView
    private lateinit var swipeRereshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.swipeRereshLayout = findViewById(R.id.swipeRereshLayout)
        this.folderListView = findViewById(R.id.folderListView)

        this.folderListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var adapter = VideoFolderAdapter(this, this.readVideoList())

        this.folderListView.adapter = adapter

        this.swipeRereshLayout.setOnRefreshListener {
            this.readVideoList()
            adapter.notifyDataSetChanged()

            this.swipeRereshLayout.isRefreshing = false
        }
    }

    private fun readVideoList(): List<String> {

        var folderList: MutableList<String> = mutableListOf()

        var videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var cursor = contentResolver.query(videoUri, null, null, null, null)

        if (cursor != null && cursor.moveToNext()) {

            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA).toInt())

                var idx = path.lastIndexOf("/")
                var subString = path.subSequence(0, idx).toString()

                if (!folderList.contains(subString)) {
                    folderList.add(subString)
                }

            } while (cursor.moveToNext())
        }

        return folderList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.folder_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miRateus -> {
                var uri = Uri.parse("https://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                return true
            }
            R.id.miRefresh -> {
                this.swipeRereshLayout.isRefreshing = true
                this.readVideoList()
                this.folderListView.adapter?.notifyDataSetChanged()

                Handler().postDelayed( {
                    this.swipeRereshLayout.isRefreshing = false
                }, 1000)

                return true
            }
            R.id.miShare -> {
                startActivity(
                    Intent.createChooser(
                        Intent()
                            .setAction(Intent.ACTION_SEND)
                            .putExtra(Intent.EXTRA_TEXT, "Check this app via\n\n" +
                                    "https://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                            .setType("text/plain")
                        ,"Share app via"
                    )
                )
                return true
            } else -> {
                return false
            }
        }
    }
}