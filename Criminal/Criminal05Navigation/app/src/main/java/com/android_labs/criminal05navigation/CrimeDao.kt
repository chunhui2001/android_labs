package com.android_labs.criminal05navigation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import java.util.UUID

@Dao
interface CrimeDao {

    @Query("select * from Crime")
    fun queryCrimeList(): LiveData<List<CrimeEntity>>

    @Query("select * from Crime where id=(:id)")
    fun getCrimeById(id: UUID): LiveData<CrimeEntity?>
}