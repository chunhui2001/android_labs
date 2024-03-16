package com.android_labs.criminalapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.UUID

class CrimeDetailsViewModel: ViewModel() {

    private var crimeIdLiveData = MutableLiveData<UUID>()
    private val crimeRepository = CrimeRepository.get()

    var currentCrime = crimeIdLiveData.switchMap { input: UUID? ->
        input?.let {
            crimeRepository.getCrimeById(it)
        }
    }

    fun loadCrimeById(uuid: UUID?) {
        uuid?.let {
            this.crimeIdLiveData.value = it
        }
    }
}