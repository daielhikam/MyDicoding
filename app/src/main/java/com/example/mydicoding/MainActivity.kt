package com.example.mydicoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragmentBottom.favorit.FragmentFavorit
import com.example.fragmentBottom.finished.FinishedFragment
import com.example.fragmentBottom.setting.SettingFragment
import com.example.fragmentBottom.upcomming.FragmentUpcoming
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(FragmentUpcoming())
        }

        // Handle item selection
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