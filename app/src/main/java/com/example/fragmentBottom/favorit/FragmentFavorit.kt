package com.example.fragmentBottom.favorit
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.Entity.EventAdapter
import com.example.Entity.EventViewModel
import com.example.fragmentBottom.finished.DetailFinished
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
        eventAdapter = EventAdapter {event->
            // Ketika item diklik, tampilkan detail dari event tersebut
            val intent = Intent(context, DetailFinished::class.java).apply {
                // Kirimkan data yang diperlukan untuk DetailFinished
                putExtra("EVENT_NAME", event.eventName)
                putExtra("EVENT_DESC", event.description)
                putExtra("EVENT_CATEGORY", event.category)
                putExtra("EVENT_OWNER_NAME", event.ownerName)
                putExtra("EVENT_CITY", event.cityName)
                putExtra("EVENT_SUMMARY", event.summary)
                putExtra("EVENT_BEGIN_TIME", event.beginTime)
                putExtra("EVENT_END_TIME", event.endTime)
                putExtra("EVENT_IMAGE", event.imageLogo)
                putExtra("EVENT_MEDIA_COVER", event.imageLogo)  // Jika ingin menambahkan media cover
                putExtra("EVENT_IS_FAVORITE", event.isFavorite)


            }
            startActivity(intent)  // Mulai Activity DetailFinished
        }

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