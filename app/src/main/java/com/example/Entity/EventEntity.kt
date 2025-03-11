package com.example.Entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // ID akan otomatis dibuat
    val eventName: String,
    val description: String,
    val category: String,
    val ownerName: String,
    val cityName: String,
    val summary: String,
    val beginTime: String,
    val endTime: String,
    val imageLogo: String,
    val isFavorite: Boolean,
    val isFinished: Boolean
)
