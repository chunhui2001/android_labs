package com.android_labs.criminal07dialog

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CrimeEntity::class], version = 1, exportSchema = false)
@TypeConverters(CrimeTypeConvertor::class)
abstract class CrimeDatabase: RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}