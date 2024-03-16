package com.android_labs.criminal07dialog

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Room
import java.util.UUID
import java.util.concurrent.Executors

class CrimeRepository private constructor(context: Context) {

    private val executors = Executors.newSingleThreadExecutor()
    private val database = Room.databaseBuilder(context, CrimeDatabase::class.java, "crime-database").build()
    private var crimeDao = database.crimeDao()

    fun queryCrimeList(): LiveData<List<CrimeEntity>> {
        return this.crimeDao.queryCrimeList()
    }

    fun getCrimeById(id: UUID): LiveData<CrimeEntity> {
        return this.crimeDao.getCrimeById(id)
    }

    fun update(id: UUID, crime: CrimeEntity) {
        crime.id = id

        executors.execute{
            this.crimeDao.updateCrime(crime)
        }
    }

    fun insert(crime: CrimeEntity) {
        crime.id = UUID.randomUUID()

        executors.execute{
            this.crimeDao.saveCrime(crime)
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