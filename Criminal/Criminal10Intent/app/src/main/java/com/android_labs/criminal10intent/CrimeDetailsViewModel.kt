package com.android_labs.criminal10intent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.io.File
import java.util.Date
import java.util.UUID

class CrimeDetailsViewModel: ViewModel() {

    private var crimeRepository = CrimeRepository.get()
    private var crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<CrimeEntity?> = crimeIdLiveData.switchMap { crimeId ->
        this.crimeRepository.getCrimeById(crimeId)
    }

    fun loadCrime(uuid: UUID) {
        this.crimeIdLiveData.value = uuid
    }

    fun saveCrime(crimeTitle: String, suspect: String, crimeDate: Date, isSolved: Boolean) {

        val crime = CrimeEntity()
        var crimeId: UUID? = crimeLiveData.value?.id

        crime.title = crimeTitle
        crime.suspect = suspect
        crime.date = crimeDate
        crime.isSolved = isSolved

        this.crimeRepository.saveCrime(crimeId, crime)
    }

    fun getPhotoFile(crime: CrimeEntity): File {
        return crimeRepository.getPhotoFile(crime)
    }
}