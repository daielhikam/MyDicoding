package com.example.fragmentBottom.finished

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
import com.example.data.response.ListItemsFinished
import com.example.mydicoding.R

class AdapterFinished(private val onBookmarkClick: (ListItemsFinished) -> Unit) :
    ListAdapter<ListItemsFinished, AdapterFinished.FinishedViewHolder>(FinishedDiffCallback()) {

    class FinishedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImage: ImageView = view.findViewById(R.id.eventImage)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)
        val tvOwnerName: TextView = view.findViewById(R.id.tvOwnerName)
        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvEventSummary: TextView = view.findViewById(R.id.tvSummary)
        val tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
        // Hapus ivBookmark dari sini
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_akan_datang, parent, false)
        return FinishedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinishedViewHolder, position: Int) {
        val event = getItem(position)

        // Set data ke TextView dan ImageView
        holder.eventTitle.text = event.name
        holder.tvCategory.text = event.category
        holder.tvOwnerName.text = event.ownerName
        holder.tvCity.text = event.cityName
        holder.tvEventSummary.text = event.summary
        holder.tvDateTime.text = "${event.beginTime} - ${event.endTime}"

        Glide.with(holder.itemView.context)
            .load(event.mediaCover)
            .into(holder.eventImage)

        // Handle item click untuk membuka Detail
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailFinished::class.java).apply {
                // Kirim semua data ke DetailFinished
                putExtra("EVENT_ID", event.id)  // Pastikan ID dikirim
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
                putExtra("EVENT_IS_FAVORITE", event.isFavorite)  // Kirim status favorit ke detail
            }
            context.startActivity(intent)
        }
    }
}

class FinishedDiffCallback : DiffUtil.ItemCallback<ListItemsFinished>() {
    override fun areItemsTheSame(oldItem: ListItemsFinished, newItem: ListItemsFinished): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItemsFinished, newItem: ListItemsFinished): Boolean {
        return oldItem == newItem
    }
}