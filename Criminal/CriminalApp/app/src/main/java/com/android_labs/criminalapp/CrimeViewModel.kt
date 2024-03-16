package com.android_labs.criminalapp

import androidx.lifecycle.ViewModel

class CrimeViewModel : ViewModel() {

//    var crimeList = mutableListOf<CrimeDataModel>()
//
//    init {
//
//        for (i in 0 until 100) {
//            var crime = CrimeDataModel()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 == 0
//            crimeList += crime
//        }
//
//    }

    private val crimeRepository = CrimeRepository.get()

    var crimeList = crimeRepository.queryCrimeList()
}