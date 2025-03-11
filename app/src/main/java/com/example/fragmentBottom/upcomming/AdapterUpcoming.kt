package com.example.fragmentBottom.upcomming

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.response.ListItemsUpcoming
import com.example.mydicoding.R

// Pastikan EventAdapter menggunakan ListAdapter dan bukan RecyclerView.Adapter biasa
class AdapterUpcoming :
    ListAdapter<ListItemsUpcoming, AdapterUpcoming.EventViewHolder>(EventDiffCallback()) {

    // ViewHolder untuk setiap item event
    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImage: ImageView = view.findViewById(R.id.eventImage)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)

//        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvOwnerName: TextView = view.findViewById(R.id.tvOwnerName)
        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvEventSummary: TextView = view.findViewById(R.id.tvSummary)
        val tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
//        val btnEventLink: Button = view.findViewById(R.id.btnEventLink)
    }

    // Override onCreateViewHolder untuk membuat ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_akan_datang, parent, false)
        return EventViewHolder(view)
    }

    // Override onBindViewHolder untuk mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)  // Gunakan getItem untuk mendapatkan item dari ListAdapter

        // Bind data ke TextViews
        holder.eventTitle.text = event.name
        holder.tvCategory.text = event.category
        holder.tvOwnerName.text = event.ownerName
        holder.tvCity.text = event.cityName
        holder.tvEventSummary.text = event.summary
        holder.tvDateTime.text = "${event.beginTime} - ${event.endTime}"

        // Load gambar menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(event.mediaCover)
            .into(holder.eventImage)




        // Menambahkan klik listener untuk berpindah ke DetailUpcomingActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailUpcoming::class.java).apply {
                putExtra("EVENT_DESC", event.description)
                putExtra("EVENT_QUOTA", event.quota)
                putExtra("EVENT_LINK", event.link)
                putExtra("EVENT_REGIST", event.registrants)
                putExtra("EVENT_NAME", event.name)
                putExtra("EVENT_CATEGORY", event.category)
                putExtra("EVENT_OWNER", event.ownerName)
                putExtra("EVENT_CITY", event.cityName)
                putExtra("EVENT_SUMMARY", event.summary)
                putExtra("EVENT_BEGIN_TIME", event.beginTime)
                putExtra("EVENT_END_TIME", event.endTime)
                putExtra("EVENT_IMAGE", event.mediaCover)
            }
            context.startActivity(intent)
        }
    }

    // DiffUtil untuk membandingkan data dan memastikan perubahan yang efisien pada RecyclerView
    class EventDiffCallback : DiffUtil.ItemCallback<ListItemsUpcoming>() {
        override fun areItemsTheSame(
            oldItem: ListItemsUpcoming,
            newItem: ListItemsUpcoming
        ): Boolean {
            // Bandingkan item berdasarkan ID unik (misalnya ID event)
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ListItemsUpcoming,
            newItem: ListItemsUpcoming
        ): Boolean {
            // Bandingkan apakah konten itemnya sama (misalnya nama event, tanggal)
            return oldItem == newItem
        }
    }
}