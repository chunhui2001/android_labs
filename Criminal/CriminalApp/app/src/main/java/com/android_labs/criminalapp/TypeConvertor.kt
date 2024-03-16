package com.android_labs.criminalapp

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class TypeConvertor {

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return uuid?.let {
            UUID.fromString(it)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let {
            Date(date)
        }
    }
}