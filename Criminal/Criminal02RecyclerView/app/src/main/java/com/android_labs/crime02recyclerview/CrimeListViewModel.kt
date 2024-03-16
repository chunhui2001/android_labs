package com.android_labs.crime02recyclerview

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    var crimeList = mutableListOf<CrimeDataModel>()

    init {
        for (i in 0 until 100) {
            var crime = CrimeDataModel()
            crime.title = "Crime #$i"
            crimeList += crime
        }
    }
}