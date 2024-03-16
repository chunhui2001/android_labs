package com.android_labs.criminal04room

import androidx.lifecycle.ViewModel

class CrimeViewModel: ViewModel() {

//    var crimeList = mutableListOf<CrimeEntity>()
//
//    init {
//        for (i in 0 until  100) {
//            var crime = CrimeEntity()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 ==0
//            crimeList += crime
//        }
//    }

    var crimeRepository = CrimeRepository.get()
    var crimeList = crimeRepository.queryCrimeList()
}