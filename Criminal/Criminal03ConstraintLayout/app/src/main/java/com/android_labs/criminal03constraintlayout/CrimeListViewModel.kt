package com.android_labs.criminal03constraintlayout

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    var crimeList = mutableListOf<CrimeDataModel>()

    init{
        for(i in 0 until 100) {
            var crime = CrimeDataModel()
            crime.title = "Crime #$i"
            crime.solved = i % 2 == 0
            crimeList += crime
        }
    }
}