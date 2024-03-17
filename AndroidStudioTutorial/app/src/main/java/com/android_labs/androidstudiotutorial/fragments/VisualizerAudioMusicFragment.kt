package com.android_labs.androidstudiotutorial.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.gauravk.audiovisualizer.visualizer.BarVisualizer

class VisualizerAudioMusicFragment: Fragment() {

    private lateinit var audioPlay: Button
    private lateinit var audioPause: Button
    private lateinit var audioStop: Button

    private var player: MediaPlayer? = null
    private lateinit var barVisualizer: BarVisualizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.RECORD_AUDIO)){

                Toast.makeText(requireContext(), "EXPLANATION", Toast.LENGTH_LONG).show()
            }
            else{
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    1)

                Toast.makeText(requireContext(), "EXPLANATION NOT NEEDED", Toast.LENGTH_LONG).show()
            }
        }

        val view = inflater.inflate(R.layout.fragment_visualizer_audio, container, false)

        this.audioPlay = view.findViewById(R.id.audioPlay)
        this.audioPause = view.findViewById(R.id.audioPause)
        this.audioStop = view.findViewById(R.id.audioStop)
        this.barVisualizer = view.findViewById(R.id.barVisualizer)

        this.audioPlay.setOnClickListener {
            if (this.player == null) {
                this.player = MediaPlayer.create(requireContext(), R.raw.seeyouagain)


                val audioSessionId = this.player?.audioSessionId!!

                if (audioSessionId != -1) {
                    this.barVisualizer.setAudioSessionId(audioSessionId)
                }
            }

            this.player?.start()
        }

        this.audioPause.setOnClickListener {
            this.player?.pause()
        }

        this.audioStop.setOnClickListener {
            this.player?.stop()
            this.player?.release()
            this.player = null

            this.barVisualizer.release()
        }

        return view
    }
}