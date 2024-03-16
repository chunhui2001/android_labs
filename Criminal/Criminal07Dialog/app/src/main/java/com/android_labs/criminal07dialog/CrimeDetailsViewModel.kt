package com.android_labs.criminal07dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.Date
import java.util.UUID

class CrimeDetailsViewModel: ViewModel() {

    private var crimeRepository = CrimeRepository.get()
    private var crimeIdLiveDate = MutableLiveData<UUID?>()

    var currentCrimeLiveData = crimeIdLiveDate.switchMap { uuid ->
        uuid?.let {
            crimeRepository.getCrimeById(it)
        }
    }

    fun loadCrimeById(uuid: UUID) {
        crimeIdLiveDate.value = uuid
    }

    fun saveCrime(title: String, date: Date, isSolved: Boolean) {

        var crime = CrimeEntity()

        crime.title = title
        crime.date = date
        crime.isSolved = isSolved

        if (crimeIdLiveDate.value == null) {
            crimeRepository.insert(crime)
        } else {
            crimeIdLiveDate.value?.let {
                crimeRepository.update(it, crime)
            }
        }
    }

}