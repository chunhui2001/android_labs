package com.android_labs.criminal07dialog

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    private var crimeRepository = CrimeRepository.get()
    var crimeList = crimeRepository.queryCrimeList()
}