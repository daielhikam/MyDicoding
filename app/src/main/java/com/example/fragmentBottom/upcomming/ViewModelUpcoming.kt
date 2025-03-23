package com.example.fragmentBottom.upcomming

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.data.retrofit.ApiConfig
import com.example.data.response.ListItemsUpcoming
import com.example.reminder.ReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ViewModelUpcoming : ViewModel() {

    private val _events = MutableLiveData<List<ListItemsUpcoming>>()
    val events: LiveData<List<ListItemsUpcoming>> get() = _events

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun scheduleEventNotification(context: Context) {
        if (_events.value.isNullOrEmpty()) {
            getEvents()
        }

        events.observeForever { eventList ->
            if (!eventList.isNullOrEmpty()) {
                val event = eventList.first()

                val data = workDataOf(
                    "EVENT_NAME" to event.name,
                    "EVENT_TIME" to event.beginTime,
                    "EVENT_IMAGE" to event.mediaCover
                )

                val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(15, TimeUnit.MINUTES)
                    .setInputData(data)
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiresBatteryNotLow(true)
                            .setRequiresDeviceIdle(false)
                            .build()
                    )
                    .build()

                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    "daily_reminder",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    workRequest
                )
            }
        }
    }

    fun getEvents(forceRefresh: Boolean = false) {
        if (_events.value.isNullOrEmpty() || forceRefresh) {
            // Hanya fetch jika data kosong atau refresh dipaksa
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
                        _error.postValue("Terjadi kesalahan pada server.")
                    }
                } catch (e: Exception) {
                    _error.postValue("Terjadi kesalahan: ${e.message}")
                } finally {
                    _loading.postValue(false)
                }
            }
        }
    }
}