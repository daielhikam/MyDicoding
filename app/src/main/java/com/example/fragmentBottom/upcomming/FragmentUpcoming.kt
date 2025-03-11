package com.example.fragmentBottom.upcomming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.response.ListItemsUpcoming
import com.example.mydicoding.databinding.FragmentUpcomingBinding

class FragmentUpcoming : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModelUpcoming by viewModels()
    private lateinit var adapter: AdapterUpcoming
    private var allEvents: List<ListItemsUpcoming> = emptyList() // Simpan semua data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterUpcoming()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FragmentUpcoming.adapter
        }

        // Memanggil fungsi untuk mengambil data events
        viewModel.getEvents()

        // Observasi perubahan data events
        viewModel.events.observe(viewLifecycleOwner) { events ->
            binding.progressBar.visibility = View.GONE
            allEvents = events
            adapter.submitList(events)
        }

        // Observasi perubahan error message
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}