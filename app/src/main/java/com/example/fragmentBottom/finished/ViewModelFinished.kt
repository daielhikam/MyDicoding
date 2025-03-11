package com.example.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.response.ListItemsFinished
import com.example.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ViewModelFinished : ViewModel() {

    private val _events = MutableLiveData<List<ListItemsFinished>>()
    val events: LiveData<List<ListItemsFinished>> get() = _events

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // MutableLiveData untuk menyimpan status favorit
    private val _favoriteEvents = MutableLiveData<Map<Int, Boolean>>()
    val favoriteEvents: LiveData<Map<Int, Boolean>> get() = _favoriteEvents

    init {
        // Menginisialisasi status favorit dengan data kosong atau status default
        _favoriteEvents.value = emptyMap()
    }

    // Fetch data event
    fun fetchFinishedEvents() {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService.getFinished()
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    _events.postValue(events)
                    // Menyimpan status favorit berdasarkan data event
                    val favoriteStatus = events.associate { it.id to it.isFavorite }
                    _favoriteEvents.postValue(favoriteStatus)
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.message}")
            }
        }
    }

    // Fungsi untuk toggle status favorit
    fun toggleFavorite(event: ListItemsFinished) {
        viewModelScope.launch {
            // Dapatkan status favorit yang ada
            val currentFavorites = _favoriteEvents.value?.toMutableMap() ?: mutableMapOf()
            val currentStatus = currentFavorites[event.id] ?: false
            // Toggle status favorit
            currentFavorites[event.id] = !currentStatus
            _favoriteEvents.postValue(currentFavorites)

        }
    }
}