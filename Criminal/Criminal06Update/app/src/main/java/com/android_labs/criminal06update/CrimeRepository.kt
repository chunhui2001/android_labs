package com.android_labs.criminal06update

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Room
import androidx.room.Update
import java.lang.IllegalStateException
import java.util.UUID
import java.util.concurrent.Executors

private const val CRIME_DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database = Room.databaseBuilder(context, CrimeDatabase::class.java, CRIME_DATABASE_NAME).build()

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun queryCrimeList(): LiveData<List<CrimeEntity>> {
        return crimeDao.queryCrimeList()
    }

    fun getCrimeById(id: UUID): LiveData<CrimeEntity> {
        return crimeDao.getCrimeById(id)
    }

    fun insertCrime(crime: CrimeEntity) {
        executor.execute {
            crimeDao.insertCrime(crime)
        }
    }

    fun updateCrime(crime: CrimeEntity) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE?: throw IllegalStateException("CrimeRepository must be initialized!")
        }
    }
}