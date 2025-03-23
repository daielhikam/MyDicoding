package com.example.mydicoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fragmentBottom.favorit.FragmentFavorit
import com.example.fragmentBottom.finished.FinishedFragment
import com.example.fragmentBottom.setting.SettingFragment
import com.example.fragmentBottom.setting.SettingPreferences
import com.example.fragmentBottom.setting.dataStore
import com.example.fragmentBottom.upcomming.FragmentUpcoming
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val settingPreferences = SettingPreferences.getInstance(applicationContext.dataStore)
        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(FragmentUpcoming())
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_upcoming -> loadFragment(FragmentUpcoming())
                R.id.nav_finished -> loadFragment(FinishedFragment())
                R.id.nav_favorite -> loadFragment(FragmentFavorit())
                R.id.nav_settings -> loadFragment(SettingFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}