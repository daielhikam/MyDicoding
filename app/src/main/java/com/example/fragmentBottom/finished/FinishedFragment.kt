package com.example.fragmentBottom.finished

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.response.ListItemsFinished
import com.example.mydicoding.databinding.FragmentFinishedBinding
import com.example.ui.finished.ViewModelFinished

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModelFinished by viewModels()
    private lateinit var adapter: AdapterFinished
    private var allEvents: List<ListItemsFinished> = emptyList() // Simpan semua data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menginisialisasi adapter dengan callback onBookmarkClick
        adapter = AdapterFinished { event ->
            // Mengubah status favorit di ViewModel saat bookmark diklik
            viewModel.toggleFavorite(event)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FinishedFragment.adapter
        }

        // Ambil data event dari ViewModel
        viewModel.fetchFinishedEvents()

        // Observasi perubahan data events
        viewModel.events.observe(viewLifecycleOwner) { events ->
            binding.progressBar.visibility = View.GONE
            allEvents = events // Simpan semua event
            adapter.submitList(events)
        }

        // Observasi perubahan error message
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Implementasi SearchBar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    // Fungsi untuk filter berdasarkan huruf yang dicari
    private fun filterList(query: String?) {
        val filteredList = if (!query.isNullOrEmpty()) {
            allEvents.filter { it.name.contains(query, ignoreCase = true) }
        } else {
            allEvents // Jika kosong, tampilkan semua
        }
        adapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}