package com.android_labs.criminalapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import java.util.UUID

@Dao
interface CrimeDao {

    @Query("select * from Crime")
    fun queryCrimeList(): LiveData<List<CrimeDataModel>>

    @Query("select * from Crime where id=(:id)")
    fun getCrimeById(id: UUID): LiveData<CrimeDataModel>
}