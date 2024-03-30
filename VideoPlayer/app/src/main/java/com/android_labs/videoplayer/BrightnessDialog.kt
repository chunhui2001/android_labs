package com.android_labs.videoplayer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment

class BrightnessDialog: AppCompatDialogFragment() {

    private lateinit var cross: ImageView
    private lateinit var brightnessValue: TextView
    private lateinit var seekBar: SeekBar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = AlertDialog.Builder(activity)
        var view = activity?.layoutInflater?.inflate(R.layout.bright_ness_dialog_layout, null)

        dialog.setView(view)

        activity?.volumeControlStream = AudioManager.STREAM_MUSIC

        cross = view?.findViewById(R.id.closeDialog)!!
        brightnessValue = view?.findViewById(R.id.brightness_value_number)!!
        seekBar = view?.findViewById(R.id.volume_value_bar)!!

        var brightness = Settings.System.getInt(context?.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)

        seekBar.progress = brightness

        brightnessValue.text = brightness.toString()

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var ctx = context?.applicationContext

                var canWrite = Settings.System.canWrite(ctx)

                if (canWrite) {
                    var sBrightness = progress * 255 / 255
                    brightnessValue.text = sBrightness.toString()

                    Settings.System.putInt(ctx?.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                    Settings.System.putInt(ctx?.contentResolver, Settings.System.SCREEN_BRIGHTNESS, sBrightness)
                } else {
                    Toast.makeText(ctx, "Enable write settings for brightness control: " + context?.applicationContext?.packageName + ".", Toast.LENGTH_SHORT).show()
                    var intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.setData(Uri.parse("package:" + context?.applicationContext?.packageName))

                    startActivityForResult(intent, 0)
                }
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