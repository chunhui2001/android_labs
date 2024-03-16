package com.android_labs.criminal10intent

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//@Database(entities = [CrimeEntity::class], version = 1)
@Database(entities = [CrimeEntity::class], version = 2)
@TypeConverters(CrimeTypeConvertor::class)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}

var migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "alter table Crime add column suspect text not null default ''"
        )
    }
}
