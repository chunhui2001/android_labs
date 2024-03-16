package com.android_labs.criminal05navigation

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class CrimeTypeConvertor {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return uuid.let {
            UUID.fromString(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}