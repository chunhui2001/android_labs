package com.android_labs.travelapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.io.Serializable

class DetailsActivity : AppCompatActivity() {

    private lateinit var titleTxt: TextView
    private lateinit var locationTxt: TextView
    private lateinit var bedTxt: TextView
    private lateinit var guideTxt: TextView
    private lateinit var wifiTxt: TextView
    private lateinit var descriptionTxt: TextView
    private lateinit var scoreTxt: TextView
    private lateinit var priceTxt: TextView
    private lateinit var picImg: ImageView

    private lateinit var backBtn: ImageView

    private lateinit var city: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        this.titleTxt = findViewById(R.id.titleTxt)
        this.locationTxt = findViewById(R.id.locationTxt)
        this.bedTxt = findViewById(R.id.bedTxt)
        this.guideTxt = findViewById(R.id.guideTxt)
        this.wifiTxt = findViewById(R.id.wifiTxt)
        this.descriptionTxt = findViewById(R.id.descriptionTxt)
        this.scoreTxt = findViewById(R.id.scoreTxt)
        this.priceTxt = findViewById(R.id.priceTxt)
        this.picImg = findViewById(R.id.picImg)

        this.backBtn = findViewById(R.id.backBtn)

        this.backBtn.setOnClickListener {
            startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
        }

        this.city = getSerializable(this, "city", City::class.java)

        this.titleTxt.text = this.city.title
        this.locationTxt.text = this.city.location
        this.bedTxt.text = this.city.bed.toString()
        this.descriptionTxt.text = this.city.description
        this.scoreTxt.text = this.city.score.toString()
        this.priceTxt.text = this.city.price.toString()

        if (this.city.guide) {
            this.guideTxt.text = "Guide"
        } else {
            this.guideTxt.text = "No Guide"
        }

        if (this.city.wifi) {
            this.wifiTxt.text = "Wifi"
        } else {
            this.wifiTxt.text = "No Wifi"
        }

        Glide.with(this).load(getResourceId(this.city.pic)).into(this.picImg)
    }

    private fun <T : Serializable?> getSerializable(
        activity: Activity,
        name: String,
        clazz: Class<T>
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.intent.getSerializableExtra(name, clazz)!!
        else
            activity.intent.getSerializableExtra(name) as T
    }

    private fun getResourceId(name: String): Int {
        return this.resources.getIdentifier(name, "drawable", this.packageName)
    }
}