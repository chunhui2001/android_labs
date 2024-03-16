package com.android_labs.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val MAX_SOUNDS = 5
private const val SOUNDS_FOLDER = "sample_sounds"

class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>

    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    init {
        sounds = loadSounds()
    }

    private fun load(sound: Sound) {
        var afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        var soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }

    private fun loadSounds(): List<Sound> {
        try {
            val soundNames = assets.list(SOUNDS_FOLDER)?.asList()

            if (soundNames != null) {
                return soundNames.map { filename ->
                    val assetPath = "$SOUNDS_FOLDER/$filename"
                    val sound = Sound(assetPath)

                    try {
                        load(sound)
                    } catch (ioe: IOException) {
                        Log.e(TAG, "Could not load sound $filename", ioe)
                    }

                    sound
                }
            }
        } catch (e: Exception) {
            return emptyList()
        }

        return emptyList()
    }
}