package com.android_labs.beatbox

import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox) {

    var title: MutableLiveData<String?> = MutableLiveData()

    var sound: Sound? = null
        set(sound) {
            field = sound
            title.postValue(sound?.name)
        }

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }
}