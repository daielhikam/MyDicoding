package com.example.fragmentBottom.upcomming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.retrofit.ApiConfig
import com.example.data.response.ListItemsUpcoming
import kotlinx.coroutines.launch

class ViewModelUpcoming : ViewModel() {

    // LiveData untuk menampung data events
    private val _events = MutableLiveData<List<ListItemsUpcoming>>()
    val events: LiveData<List<ListItemsUpcoming>> get() = _events

    // LiveData untuk menampung status loading
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // LiveData untuk menampung pesan error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Fungsi untuk mengambil data events
    fun getEvents() {
        // Set loading ke true ketika data sedang dimuat
        _loading.postValue(true)

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService.getUpcoming()
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        if (!result.error && result.listEvents.isNotEmpty()) {
                            _events.postValue(result.listEvents)
                        } else {
                            _events.postValue(emptyList())
                            _error.postValue("Tidak ada data yang ditemukan.")
                        }
                    }
                } else {
                    _events.postValue(emptyList())
                    _error.postValue("Terjadi kesalahan pada server.")
                }

            } catch (e: Exception) {
                // Tangani exception jika terjadi error pada API call
                _error.postValue("Terjadi kesalahan: ${e.message}")
                e.printStackTrace()
                _events.postValue(emptyList())
            } finally {
                // Set loading ke false setelah data selesai dimuat
                _loading.postValue(false)
            }
        }
    }
}