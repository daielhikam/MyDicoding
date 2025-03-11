package com.example.Entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Insert
    suspend fun insert(event: EventEntity)

    @Update
    suspend fun update(event: EventEntity)

    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): LiveData<List<EventEntity>>  // Menggunakan LiveData

    @Query("SELECT * FROM favorite_events WHERE eventName = :eventName LIMIT 1")
    fun getEventByName(eventName: String): LiveData<EventEntity?>

    @Query("DELETE FROM favorite_events WHERE eventName = :eventName")
    suspend fun deleteFavorite(eventName: String)
}
