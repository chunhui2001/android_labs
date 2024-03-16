package com.android_labs.criminal06update

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CrimeEntity::class], version = 1, exportSchema = false)
@TypeConverters(CrimeTypeConvertor::class)
abstract class CrimeDatabase: RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}