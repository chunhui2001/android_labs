package com.android_labs.criminal05navigation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Room
import java.util.UUID

private const val CRIME_DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database = Room.databaseBuilder(context, CrimeDatabase::class.java, CRIME_DATABASE_NAME).build()

    private val crimeDao = database.crimeDao()

    fun queryCrimeList(): LiveData<List<CrimeEntity>> {
        return this.crimeDao.queryCrimeList()
    }

    fun getCrimeById(id: UUID): LiveData<CrimeEntity?> {
        return this.crimeDao.getCrimeById(id)
    }

    companion object {

        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE?: throw IllegalStateException("CrimeRepository must be initialzed")
        }
    }
}