package com.android_labs.criminal10intent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update
import java.io.File
import java.util.UUID
import java.util.concurrent.Executors

private const val CRIME_DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val executors = Executors.newSingleThreadExecutor()
    private val database = Room.databaseBuilder(context, CrimeDatabase::class.java, CRIME_DATABASE_NAME).addMigrations(migration_1_2).build()
    private val filesDir = context.applicationContext.filesDir

    private val crimeDao = database.crimeDao()

    fun getPhotoFile(crime: CrimeEntity): File = File(filesDir, crime.photoFileName)

    fun queryCrimeList(): LiveData<List<CrimeEntity>> {
        return this.crimeDao.queryCrimeList()
    }

    fun getCrimeById(id: UUID): LiveData<CrimeEntity?> {
        return this.crimeDao.getCrimeById(id)
    }

    fun saveCrime(crimeId: UUID?, crime: CrimeEntity) {
        executors.execute {
            crimeId.apply {
                when {
                    this == null -> {
                        crime.id = UUID.randomUUID()
                        crimeDao.insertCrime(crime)
                    }

                    this != null -> {
                        crime.id = this
                        crimeDao.updateCrime(crime)
                    }
                }
            }
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
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}