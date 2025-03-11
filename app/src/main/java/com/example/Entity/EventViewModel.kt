package com.example.Entity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val eventDao: EventDao = EventDatabase.getDatabase(application).eventDao()
    val allFavorites: LiveData<List<EventEntity>> = eventDao.getAllFavorites()  // LiveData untuk daftar event favorit

    // Fungsi untuk menambah event ke favorit
    fun addToFavorites(event: EventEntity) {
        viewModelScope.launch {
            eventDao.insert(event)
        }
    }

    // Fungsi untuk menghapus event dari favorit
    fun removeFromFavorites(eventName: String) {
        viewModelScope.launch {
            eventDao.deleteFavorite(eventName)
        }
    }

//    // Mengambil seluruh event favorit
//    fun getAllEvents(): LiveData<List<EventEntity>> {
//        return allEvents
//    }

    // Mengambil satu event berdasarkan eventName (untuk memeriksa status favorit saat pertama kali)
    fun getEventByName(eventName: String): LiveData<EventEntity?> {
        return eventDao.getEventByName(eventName)
    }
}
