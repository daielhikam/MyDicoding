package com.example.Entity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicoding.databinding.ItemEventBinding

class EventAdapter(private val onItemClick: (EventEntity)-> Unit) :
    ListAdapter<EventEntity, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // Menambahkan view binding untuk item layout
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            // Mengikat data ke tampilan menggunakan binding
            binding.eventTitle.text = event.eventName
            binding.tvSummary.text = event.summary
            Glide.with(binding.imgLogoEvent.context)
                .load(event.imageLogo) // eventImage adalah URL gambar yang disimpan dalam EventEntity
                .into(binding.imgLogoEvent) // eventImage adalah ImageView tempat gambar akan ditampilkan

            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    // DiffUtil untuk membandingkan item-event
    class EventDiffCallback : DiffUtil.ItemCallback<EventEntity>() {
        override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
            return oldItem == newItem
        }
    }
}