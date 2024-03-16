package com.android_labs.criminal01fragment

import java.util.Date
import java.util.UUID

data class CrimeDataModel (
    var id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
)