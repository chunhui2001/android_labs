package com.android_labs.travelapp

import java.io.Serializable

data class City(
    var title: String,
    var location: String,
    var bed: Int,
    var guide: Boolean,
    var score: Double,
    var pic: String,
    var wifi: Boolean,
    var price: Double,
    var description: String,
): Serializable
