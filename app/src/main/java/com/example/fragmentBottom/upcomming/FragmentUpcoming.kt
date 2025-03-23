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
    private var allEvents: List<ListItemsUpcoming> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.scheduleEventNotification(requireContext())

        adapter = AdapterUpcoming()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FragmentUpcoming.adapter
        }

        if (viewModel.events.value.isNullOrEmpty()) {
            viewModel.getEvents() // Hanya panggil jika data masih kosong
        } else {
            binding.progressBar.visibility = View.GONE
            adapter.submitList(viewModel.events.value) // Langsung pakai data lama
        }

        viewModel.events.observe(viewLifecycleOwner) { events ->
            binding.progressBar.visibility = View.GONE
            adapter.submitList(events)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

