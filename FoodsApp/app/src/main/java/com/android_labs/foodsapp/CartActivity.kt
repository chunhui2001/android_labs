package com.android_labs.foodsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity(), CartListAdapter.Callbacks {

    private lateinit var cartListView: RecyclerView

    private lateinit var managementCart: ManagementCart

    private lateinit var totalFeeTxt: TextView
    private lateinit var deliveryTxt: TextView
    private lateinit var taxTxt: TextView
    private lateinit var totalTxt: TextView
    private lateinit var emptyTxt: TextView
    private lateinit var scrollView4: ScrollView
    private lateinit var btnHome: LinearLayout

    override fun onCountChanged(foodName: String, currentCount: Int) {
        Log.d("onCountChanged", "foodName=$foodName, currentCount=$currentCount")

        managementCart.updateFoodCount(foodName, currentCount)

        var list = managementCart.getFoodList();

        this.cartListView.adapter = CartListAdapter(list, this@CartActivity)

        this.totalTxt.text = managementCart.totalMoney().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        managementCart = ManagementCart(this)

        var list = managementCart.getFoodList();

        this.btnHome = findViewById(R.id.btnHome)
        this.cartListView = findViewById(R.id.cartListView)
        this.cartListView.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
        this.cartListView.adapter = CartListAdapter(list, this@CartActivity)

        this.totalFeeTxt = findViewById(R.id.totalFeeTxt)
        this.deliveryTxt = findViewById(R.id.deliveryTxt)
        this.taxTxt = findViewById(R.id.taxTxt)
        this.totalTxt = findViewById(R.id.totalTxt)
        this.emptyTxt = findViewById(R.id.emptyTxt)
        this.scrollView4 = findViewById(R.id.scrollView4)

        this.totalTxt.text = managementCart.totalMoney().toString()

        if (list.isEmpty()) {
            this.emptyTxt.apply {
                visibility = View.VISIBLE
            }
            this.scrollView4.apply {
                visibility = View.INVISIBLE
            }
        }

        this.btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}