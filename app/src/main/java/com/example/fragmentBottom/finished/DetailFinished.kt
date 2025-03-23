package com.example.fragmentBottom.finished

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.entity.EventEntity
import com.example.entity.EventViewModel
import com.example.mydicoding.R
import com.example.mydicoding.databinding.ActivityFinishedDetailBinding

class DetailFinished : AppCompatActivity() {

    private lateinit var binding: ActivityFinishedDetailBinding
    private val eventViewModel: EventViewModel by viewModels()

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
    private  var register: Int = 0
    private var quota: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        description = intent.getStringExtra("EVENT_DESC")?:""
        register = intent.getIntExtra("EVENT_REGIST", 0)
        quota = intent.getIntExtra("EVENT_QUOTA", 0)
        eventName = intent.getStringExtra("EVENT_NAME") ?: ""
        eventImage = intent.getStringExtra("EVENT_IMAGE") ?: ""
        category = intent.getStringExtra("EVENT_CATEGORY")?: ""
        ownerName = intent.getStringExtra("EVENT_OWNER")?: ""
        city = intent.getStringExtra("EVENT_CITY")?: ""
        summary = intent.getStringExtra("EVENT_SUMMARY")?: ""
        beginTime = intent.getStringExtra("EVENT_BEGIN_TIME")?: ""
        endTime = intent.getStringExtra("EVENT_END_TIME")?: ""
        mediaCover = intent.getStringExtra("EVENT_MEDIA_COVER")?: ""

        eventViewModel.getEventByName(eventName).observe(this, Observer { event ->
            if (event != null) {
                isFavorite = event.isFavorite
                setBookmarkIcon()  // Memperbarui ikon berdasarkan status favorit
            }
        })

        isFavorite = intent.getBooleanExtra("EVENT_IS_FAVORITE", false)

        binding.eventDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
        binding.tvName.text = eventName
        binding.tvCategory.text = category
        binding.eventOwner.text = ownerName
        binding.tvCityLokasi.text = city
        binding.tvDateTime.text = "$beginTime - $endTime"
        binding.tvSummary.text = summary
        binding.tvQuota.text = "Quota : $quota"
        binding.tvRegistrants.text = "Registrans : $register"


        Glide.with(this).load(eventImage).into(binding.eventImage)

        setBookmarkIcon()

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
                quota = quota,
                regist = register,
                isFinished = true
            )
            if (isFavorite) {
                eventViewModel.addToFavorites(eventEntity)
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            } else {
                eventViewModel.removeFromFavorites(eventName)
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setBookmarkIcon() {
        if (isFavorite) {
            // Menggunakan ContextCompat untuk mengambil drawable secara aman
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_nyatu))
        } else {
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_patah))
        }
    }
}