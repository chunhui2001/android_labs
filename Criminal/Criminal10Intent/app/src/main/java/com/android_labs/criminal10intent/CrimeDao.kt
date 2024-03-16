package com.android_labs.criminal10intent

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

@Dao
interface CrimeDao {

    @Query("select * from Crime")
    fun queryCrimeList(): LiveData<List<CrimeEntity>>

    @Query("select * from Crime where id = (:id)")
    fun getCrimeById(id: UUID): LiveData<CrimeEntity?>

    @Update
    fun updateCrime(crime: CrimeEntity)

    @Insert
    fun insertCrime(crime: CrimeEntity)
}