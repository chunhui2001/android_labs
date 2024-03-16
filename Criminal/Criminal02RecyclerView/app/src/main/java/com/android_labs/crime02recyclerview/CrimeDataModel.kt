package com.android_labs.crime02recyclerview

import java.util.Date
import java.util.UUID

data class CrimeDataModel (
    var id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var solvered: Boolean = false
)