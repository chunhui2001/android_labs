package com.android_labs.criminal10intent

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {
    var crimeRepository = CrimeRepository.get()
    var crimeList = crimeRepository.queryCrimeList()
}