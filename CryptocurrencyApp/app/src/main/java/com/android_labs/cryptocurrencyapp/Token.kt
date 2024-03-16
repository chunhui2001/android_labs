package com.android_labs.cryptocurrencyapp

class Token (
    var logo: String = "",
    var symbol: String = "",
    var price: Double,
    var changePercent: Double,
    var lineData: List<Int>,
    var amount: Double,
    var size: Double,
)