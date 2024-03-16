package com.android_labs.foodsapp

import java.io.Serializable

data class FoodModel(
    var title: String,
    var pic: String,
    var description: String,
    var fee: Double,
    var numberInCart: Int
) : Serializable
