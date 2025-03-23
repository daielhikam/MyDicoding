package com.example.fragmentBottom.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SettingViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {

    fun getThemeSettings() = settingPreferences.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(isDarkMode)
        }
    }
}