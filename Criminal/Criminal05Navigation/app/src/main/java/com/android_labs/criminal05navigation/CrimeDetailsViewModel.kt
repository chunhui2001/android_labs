package com.android_labs.criminal05navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.UUID

//import androidx.lifecycle.Transformations

class CrimeDetailsViewModel: ViewModel() {

    private var crimeIdData = MutableLiveData<UUID>()
    private val crimeRepository = CrimeRepository.get()

    var crimeLiveData: LiveData<CrimeEntity?> = crimeIdData.switchMap { input: UUID?->
        input?.let {
            crimeRepository.getCrimeById(it)
        }
    }

    fun getCrime(crimeId: UUID) {
        crimeIdData.value = crimeId
    }
}