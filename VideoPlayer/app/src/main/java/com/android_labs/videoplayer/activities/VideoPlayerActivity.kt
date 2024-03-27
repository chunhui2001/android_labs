package com.android_labs.videoplayer.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android_labs.videoplayer.MediaFiles
import com.android_labs.videoplayer.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var playerView: PlayerView
    private lateinit var simpleExoVideoPlayer: SimpleExoPlayer
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource

    private lateinit var videoTitleTxt: TextView
    private lateinit var nextVideoBtn: ImageView
    private lateinit var prevVideoBtn: ImageView

    private var videoPosition: Int = -1
    private lateinit var videoTitle: String
    private lateinit var videoPlayerList: java.util.ArrayList<MediaFiles>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        setContentView(R.layout.activity_video_player)

        this.playerView = findViewById(R.id.exoplayerView)
        this.videoTitleTxt = playerView.findViewById(R.id.videoTitleTxt)

        this.nextVideoBtn = playerView.findViewById(R.id.exoNext)
        this.prevVideoBtn = playerView.findViewById(R.id.exoPrev)

        supportActionBar?.hide()

        this.videoPosition = intent.getIntExtra("videoPosition", -1)!!
        this.videoTitle = intent.getStringExtra("videoTitle")!!

        this.videoTitleTxt.text = this.videoTitle
        this.videoPlayerList = intent.getParcelableArrayListExtra("videoPlayerList")!!

        this.simpleExoVideoPlayer = SimpleExoPlayer.Builder(this).build()

        this.playerView.player = simpleExoVideoPlayer
        this.playerView.keepScreenOn = true

        this.nextVideoBtn.setOnClickListener(this)
        this.prevVideoBtn.setOnClickListener(this)

        playVideo()
    }

    override fun onClick(v: View?) {
        v?.let {
            if (it.id == R.id.exoNext) {
                this.simpleExoVideoPlayer.stop()
                this.videoPosition++
                this.playVideo()
            }

            if (it.id == R.id.exoPrev) {
                this.simpleExoVideoPlayer.stop()
                this.videoPosition--
                this.playVideo()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (this.simpleExoVideoPlayer.isPlaying) {
            this.simpleExoVideoPlayer.stop()
        }
    }

    override fun onPause() {
        super.onPause()

        this.simpleExoVideoPlayer.playWhenReady = false
        this.simpleExoVideoPlayer.playbackState
    }

    override fun onResume() {
        super.onResume()

        this.simpleExoVideoPlayer.playWhenReady = true
        this.simpleExoVideoPlayer.playbackState
    }

    override fun onRestart() {
        super.onRestart()

        this.simpleExoVideoPlayer.playWhenReady = true
        this.simpleExoVideoPlayer.playbackState
    }

    private fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun playVideo() {
        var dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "app"))

        concatenatingMediaSource = ConcatenatingMediaSource()

        for (i in 0 until videoPlayerList.size) {
            var currFile = this.videoPlayerList[i].path!!

            var mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(currFile))

            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        this.simpleExoVideoPlayer.prepare(concatenatingMediaSource)
        this.simpleExoVideoPlayer.seekTo(this.videoPosition, C.TIME_UNSET)

        playError()
    }

    private fun playError() {

        this.simpleExoVideoPlayer.addListener(object: Player.EventListener{
            override fun onPlayerError(error: ExoPlaybackException) {
                 super.onPlayerError(error)
                Toast.makeText(this@VideoPlayerActivity, "Video Playing Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        this.simpleExoVideoPlayer.playWhenReady = true
    }
}