package com.example.fragmentBottom.favorit
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.Entity.EventAdapter
import com.example.Entity.EventViewModel
import com.example.mydicoding.databinding.FragmentFavoriteBinding

class FragmentFavorit : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var eventAdapter: EventAdapter
    private val eventViewModel: EventViewModel by activityViewModels()  // ViewModel shared dengan Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Setup RecyclerView dan Adapter
        eventAdapter = EventAdapter()
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewFavorit.adapter = eventAdapter

        // Mengamati perubahan data favorit
        eventViewModel.allFavorites.observe(viewLifecycleOwner, Observer { favoriteEvents ->
            // Update RecyclerView jika data favorit berubah
            favoriteEvents?.let {
                eventAdapter.submitList(it)  // Mengupdate data pada Adapter
            }
        })

        return binding.root
    }
}