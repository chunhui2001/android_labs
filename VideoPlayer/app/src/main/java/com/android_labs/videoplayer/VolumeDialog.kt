package com.android_labs.videoplayer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import kotlin.math.ceil

class VolumeDialog: AppCompatDialogFragment() {

    private lateinit var cross: ImageView
    private lateinit var volumeValue: TextView
    private lateinit var seekBar: SeekBar

    private lateinit var audioManager: AudioManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = AlertDialog.Builder(activity)
        var view = activity?.layoutInflater?.inflate(R.layout.volume_dialog_layout, null)

        dialog.setView(view)

        activity?.volumeControlStream = AudioManager.STREAM_MUSIC

        cross = view?.findViewById(R.id.closeDialog)!!
        volumeValue = view?.findViewById(R.id.volume_value_number)!!
        seekBar = view?.findViewById(R.id.volume_value_bar)!!

        audioManager = context?.getSystemService(Context.AUDIO_SERVICE)!! as AudioManager

        seekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        var mediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        var maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        var volPer = ceil((mediaVolume.toDouble() / maxVolume.toDouble()) * 100)

        volumeValue.text = volPer.toString()

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                var mediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                var maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                var volPer = ceil((mediaVolume.toDouble() / maxVolume.toDouble()) * 100)

                volumeValue.text = volPer.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        cross.setOnClickListener {
            dismiss()
        }

        return dialog.create()
    }
}