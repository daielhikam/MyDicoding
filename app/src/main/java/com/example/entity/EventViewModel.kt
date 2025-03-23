package com.example.entity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val eventDao: EventDao = EventDatabase.getDatabase(application).eventDao()
    val allFavorites: LiveData<List<EventEntity>> = eventDao.getAllFavorites()

    fun addToFavorites(event: EventEntity) {
        viewModelScope.launch {
            eventDao.insert(event)
        }
    }
    fun removeFromFavorites(eventName: String) {
        viewModelScope.launch {
            eventDao.deleteFavorite(eventName)
        }
    }
    fun getEventByName(eventName: String): LiveData<EventEntity?> {
        return eventDao.getEventByName(eventName)
    }
}
