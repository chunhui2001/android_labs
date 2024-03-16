package com.android_labs.criminalapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CrimeDataModel::class], version = 1, exportSchema = false)
@TypeConverters(TypeConvertor::class)
abstract class CrimeDatabase: RoomDatabase() {

    abstract fun crimeDao(): CrimeDao

}