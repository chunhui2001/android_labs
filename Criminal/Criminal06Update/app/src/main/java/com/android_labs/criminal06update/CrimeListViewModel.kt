package com.android_labs.criminal06update

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    val crimeRepository = CrimeRepository.get()
    var crimeList = crimeRepository.queryCrimeList()

}