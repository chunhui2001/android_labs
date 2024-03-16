package com.android_labs.weatherapp

data class Future(
    var day: String,
    var picPath: String,
    var status: String,
    var highTemp: Int,
    var lowTemp: Int,
)
