package com.android_labs.criminal06update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.Date
import java.util.UUID

class CrimeDetailsViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    private var crimeIdLiveData = MutableLiveData<UUID?>()

    var crimeLiveData = crimeIdLiveData.switchMap { crimeId ->
        crimeId?.let {
            crimeRepository.getCrimeById(it)
        }
    }

    fun loadCrime(uuid: UUID) {
        crimeIdLiveData.value = uuid
    }

    fun saveCrime(crimeTitle: String, date: String, isSolved: Boolean) {

        var crime = CrimeEntity()

        crime.title = crimeTitle
        crime.date = Date()
        crime.isSolved = isSolved

        if (crimeIdLiveData.value == null) {
            crime.id = UUID.randomUUID()
            crimeRepository.insertCrime(crime)
        } else {
            crimeIdLiveData.value?.let {
                crime.id = it
            }
            crimeRepository.updateCrime(crime)
        }
    }
}