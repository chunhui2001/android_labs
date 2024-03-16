package com.android_labs.criminal04room

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class CrimeTypeConvertor {

    @TypeConverter
    fun toUUID(uuid: String?) : UUID? {
        return uuid?.let {
            UUID.fromString(uuid)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let {
            Date(date)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}