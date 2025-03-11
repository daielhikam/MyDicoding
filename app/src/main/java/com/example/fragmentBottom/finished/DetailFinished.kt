package com.example.fragmentBottom.finished

import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.Entity.EventEntity
import com.example.Entity.EventViewModel
import com.example.mydicoding.R
import com.example.mydicoding.databinding.ActivityFinishedDetailBinding

class DetailFinished : AppCompatActivity() {

    private lateinit var binding: ActivityFinishedDetailBinding
    private val eventViewModel: EventViewModel by viewModels()  // Menggunakan ViewModel untuk Activity

    private var isFavorite: Boolean = false
    private lateinit var eventName: String
    private lateinit var description: String
    private lateinit var eventImage: String
    private lateinit var category: String
    private lateinit var ownerName: String
    private lateinit var city: String
    private lateinit var summary: String
    private lateinit var beginTime: String
    private lateinit var endTime: String
    private lateinit var mediaCover: String
    private lateinit var quota: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        description = intent.getStringExtra("EVENT_DESC")?:""
        eventName = intent.getStringExtra("EVENT_NAME") ?: ""
        eventImage = intent.getStringExtra("EVENT_IMAGE") ?: ""
        category = intent.getStringExtra("EVENT_CATEGORY")?: ""
        ownerName = intent.getStringExtra("EVENT_OWNER_NAME")?: ""
        city = intent.getStringExtra("EVENT_CITY")?: ""
        summary = intent.getStringExtra("EVENT_SUMMARY")?: ""
        beginTime = intent.getStringExtra("EVENT_BEGIN_TIME")?: ""
        endTime = intent.getStringExtra("EVENT_END_TIME")?: ""
        mediaCover = intent.getStringExtra("EVENT_MEDIA_COVER")?: ""



        // Periksa status favorit dari database
        eventViewModel.getEventByName(eventName).observe(this, Observer { event ->
            if (event != null) {
                isFavorite = event.isFavorite
                setBookmarkIcon()  // Memperbarui ikon berdasarkan status favorit
            }
        })

        // Ambil status favorit dari Intent
        isFavorite = intent.getBooleanExtra("EVENT_IS_FAVORITE", false)


        // Menampilkan data di UI
        binding.eventDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
        binding.tvName.text = eventName
        binding.tvCategory.text = category
        binding.eventOwner.text = ownerName
        binding.tvCityLokasi.text = city
        binding.tvDateTime.text = "$beginTime - $endTime"
        binding.tvSummary.text = summary
        Glide.with(this).load(eventImage).into(binding.eventImage)

        // Set ikon bookmark berdasarkan status favorit
        setBookmarkIcon()

        // Tangani klik pada ikon bookmark
        binding.ivBookmark.setOnClickListener {
            isFavorite = !isFavorite
            setBookmarkIcon()


            val eventEntity = EventEntity(
                eventName = eventName,
                description = description,
                imageLogo = eventImage,
                category = category,
                isFavorite = isFavorite,
                ownerName = ownerName,
                cityName = city,
                beginTime = beginTime,
                endTime = endTime,
                summary = summary,
                isFinished = true  // Menjadikan event sebagai finished
            )

            // Tambahkan atau hapus event dari favorit menggunakan ViewModel
            if (isFavorite) {
                eventViewModel.addToFavorites(eventEntity)
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            } else {
                eventViewModel.removeFromFavorites(eventName)
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk mengubah ikon bookmark
    private fun setBookmarkIcon() {
        if (isFavorite) {
            // Menggunakan ContextCompat untuk mengambil drawable secara aman
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_nyatu))
        } else {
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_patah))
        }
    }
}