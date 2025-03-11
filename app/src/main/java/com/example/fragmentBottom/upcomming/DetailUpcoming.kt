package com.example.fragmentBottom.upcomming

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mydicoding.databinding.ActivityUpcomingDetailBinding

class DetailUpcoming : AppCompatActivity() {

    private lateinit var binding: ActivityUpcomingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpcomingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        val description = intent.getStringExtra("EVENT_DESC")
        val regist = intent.getIntExtra("EVENT_REGIST",0)
        val quota = intent.getIntExtra("EVENT_QUOTA",0)
        val link = intent.getStringExtra("EVENT_LINK")
        val eventName = intent.getStringExtra("EVENT_NAME")
        val eventCategory = intent.getStringExtra("EVENT_CATEGORY")
        val eventOwner = intent.getStringExtra("EVENT_OWNER")
        val eventCity = intent.getStringExtra("EVENT_CITY")
        val eventSummary = intent.getStringExtra("EVENT_SUMMARY")
        val eventBeginTime = intent.getStringExtra("EVENT_BEGIN_TIME")
        val eventEndTime = intent.getStringExtra("EVENT_END_TIME")
        val eventImage = intent.getStringExtra("EVENT_IMAGE")

        // Set data ke tampilan
        binding.eventDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY) // Menampilkan HTML dengan benar
        binding.tvName.text = eventName
        binding.tvRegistrants.text = "Registrans : $regist"
        binding.tvQuota.text = "Quota : $quota"
        binding.tvCategory.text = eventCategory
        binding.eventOwner.text = eventOwner
        binding.tvCityLokasi.text = eventCity
        binding.tvSummary.text = eventSummary
        binding.tvDateTime.text = "$eventBeginTime - $eventEndTime"

        Glide.with(this)
            .load(eventImage)
            .into(binding.eventImage)


        //membuka link
        binding.btnEventLink.setOnClickListener {
             val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
             startActivity(browserIntent)
        }
    }
}
