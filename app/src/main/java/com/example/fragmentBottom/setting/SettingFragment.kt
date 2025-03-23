package com.example.fragmentBottom.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fragmentBottom.upcomming.ViewModelUpcoming
import com.example.mydicoding.R
import com.example.mydicoding.databinding.FragmentSettingBinding
import com.example.reminder.ReminderHelper
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var reminderHelper: ReminderHelper

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModelUpcoming: ViewModelUpcoming by viewModels()

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(requireContext().dataStore))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingBinding.bind(view)

        val switchTheme: SwitchMaterial = binding.switchTheme
        val switchReminder: SwitchMaterial = binding.switchReminder

        sharedPreferences = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        reminderHelper = ReminderHelper(requireContext())

        val isReminderActive = sharedPreferences.getBoolean("REMINDER_ACTIVE", false)
        switchReminder.isChecked = isReminderActive

        // **Hindari Looping: Matikan listener sementara**
        switchTheme.setOnCheckedChangeListener(null)

        // Ambil status tema dari DataStore
        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            switchTheme.setOnCheckedChangeListener(null) // Matikan listener sementara
            switchTheme.isChecked = isDarkModeActive // Set switch sesuai data

            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )

            // Aktifkan kembali listener setelah perubahan selesai
            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveThemeSetting(isChecked)
                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        // Toggle untuk Notifikasi
        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("REMINDER_ACTIVE", isChecked)
                apply()
            }

            if (isChecked) {
                viewModelUpcoming.scheduleEventNotification(requireContext())  // Aktifkan WorkManager
                Toast.makeText(requireContext(), "Notifikasi Aktif", Toast.LENGTH_SHORT).show()
            } else {
                reminderHelper.cancelDailyReminder()  // Matikan WorkManager
                Toast.makeText(requireContext(), "Notifikasi Mati", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}