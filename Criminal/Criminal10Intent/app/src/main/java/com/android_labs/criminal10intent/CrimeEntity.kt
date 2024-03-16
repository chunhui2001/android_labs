package com.android_labs.criminal10intent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "Crime")
data class CrimeEntity(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var title: String = "",
    var suspect: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
) {
    val photoFileName
        get() = "IMG_$id.jpg"
}