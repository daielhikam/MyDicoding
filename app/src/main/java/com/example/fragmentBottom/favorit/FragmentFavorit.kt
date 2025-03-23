package com.example.fragmentBottom.favorit
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.entity.EventAdapter
import com.example.entity.EventViewModel
import com.example.fragmentBottom.finished.DetailFinished
import com.example.mydicoding.databinding.FragmentFavoriteBinding

class FragmentFavorit : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var eventAdapter: EventAdapter
    private val eventViewModel: EventViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        eventAdapter = EventAdapter {event->
            val intent = Intent(context, DetailFinished::class.java).apply {
                putExtra("EVENT_NAME", event.eventName)
                putExtra("EVENT_DESC", event.description)
                putExtra("EVENT_CATEGORY", event.category)
                putExtra("EVENT_CITY", event.cityName)
                putExtra("EVENT_SUMMARY", event.summary)
                putExtra("EVENT_BEGIN_TIME", event.beginTime)
                putExtra("EVENT_END_TIME", event.endTime)
                putExtra("EVENT_IMAGE", event.imageLogo)
                putExtra("EVENT_OWNER", event.ownerName)
                putExtra("EVENT_QUOTA", event.quota)
                putExtra("EVENT_REGIST", event.regist)





            }
            startActivity(intent)
        }

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewFavorit.adapter = eventAdapter

        eventViewModel.allFavorites.observe(viewLifecycleOwner, Observer { favoriteEvents ->
            favoriteEvents?.let {
                eventAdapter.submitList(it)
            }
        })

        return binding.root
    }
}