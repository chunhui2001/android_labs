package com.android_labs.videoplayer.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
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

    private lateinit var rootLayout: RelativeLayout
    private lateinit var videoBack: ImageView
    private lateinit var videoUnLock: ImageView
    private lateinit var videoLock: ImageView
    private lateinit var scaling: ImageView

    private var screenMode = 1  // 1(default) 2(full) 3(zoom)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        setContentView(R.layout.activity_video_player)

        this.playerView = findViewById(R.id.exoplayerView)
        this.videoTitleTxt = playerView.findViewById(R.id.videoTitleTxt)

        this.nextVideoBtn = playerView.findViewById(R.id.exoNext)
        this.prevVideoBtn = playerView.findViewById(R.id.exoPrev)

        this.rootLayout = playerView.findViewById(R.id.root_layout)
        this.videoBack = playerView.findViewById(R.id.videoBack)
        this.videoUnLock = playerView.findViewById(R.id.videoUnLock)
        this.videoLock = playerView.findViewById(R.id.videoLock)
        this.scaling = playerView.findViewById(R.id.scaling)

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

        this.videoBack.setOnClickListener {
            if (this.simpleExoVideoPlayer != null) {
                this.simpleExoVideoPlayer.release()
            }

            finish()
        }

        this.videoLock.setOnClickListener{
            this.rootLayout.visibility = View.VISIBLE
            this.videoLock.visibility = View.INVISIBLE
        }

        this.videoUnLock.setOnClickListener {
            this.rootLayout.visibility = View.INVISIBLE
            this.videoLock.visibility = View.VISIBLE
        }

        this.scaling.setOnClickListener{
            if (screenMode == 1) {
                // to full screen
                this.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                this.simpleExoVideoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                this.scaling.setImageResource(R.drawable.ic_full)

                screenMode = 2
            } else if (screenMode == 2) {
                // to zoom screen
                this.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                this.simpleExoVideoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                this.scaling.setImageResource(R.drawable.ic_zoom_out)

                screenMode = 3
            } else if (screenMode == 3) {
                // to default screen
                this.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                this.simpleExoVideoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                this.scaling.setImageResource(R.drawable.ic_scaling24)

                screenMode = 1
            }
        }

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