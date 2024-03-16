package com.android_labs.criminal04room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.UUID

private const val CRIME_DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database =
        Room.databaseBuilder(context, CrimeDatabase::class.java, CRIME_DATABASE_NAME).build()

    private val crimeDao = database.crimeDao()

    fun queryCrimeList(): LiveData<List<CrimeEntity>> {
        return crimeDao.queryCrimeList()
    }

    fun getCrimeById(id: UUID): LiveData<CrimeEntity> {
        return crimeDao.getCrimeById(id)
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialized(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}