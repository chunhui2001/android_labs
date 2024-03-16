package com.android_labs.foodsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.Serializable

const val CURRENT_FOOD = "com.android_labs.foodsapp::DetailActivity:FOOD"

class DetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, food: FoodModel): Intent {
            // 接收 Intent 参数
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(CURRENT_FOOD, food)
            }
        }
    }

    private lateinit var foodNameTxt: TextView
    private lateinit var foodPriceTxt: TextView
    private lateinit var foodPic: ImageView
    private lateinit var numberOrderTxt: TextView
    private lateinit var descriptionTxt: TextView
    private lateinit var addToCartBtn: TextView

    private lateinit var minBtn: ImageView
    private lateinit var plusBtn: ImageView

    private var numberOrder = 1

    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        managementCart = ManagementCart(this)

        this.foodNameTxt = findViewById(R.id.foodNameTxt)
        this.foodPriceTxt = findViewById(R.id.foodPriceTxt)
        this.foodPic = findViewById(R.id.foodPic)
        this.numberOrderTxt = findViewById(R.id.numberOrderTxt)
        this.descriptionTxt = findViewById(R.id.descriptionTxt)
        this.addToCartBtn = findViewById(R.id.addToCartBtn)

        this.minBtn = findViewById(R.id.minBtn)
        this.plusBtn = findViewById(R.id.plusBtn)

        var food = getSerializable(this, CURRENT_FOOD, FoodModel::class.java)

        this.foodNameTxt.text = food.title
        this.foodPriceTxt.text = food.fee.toString()
        this.numberOrderTxt.text = numberOrder.toString()
        this.descriptionTxt.text = food.description

        setImg(this, food.pic, this.foodPic)

        this.plusBtn.setOnClickListener {
            numberOrder += 1
            this.numberOrderTxt.text = numberOrder.toString()
        }

        this.minBtn.setOnClickListener {
            if (numberOrder >1) {
                numberOrder -= 1
            }

            this.numberOrderTxt.text = numberOrder.toString()
        }

        this.addToCartBtn.setOnClickListener {
            food.numberInCart = numberOrder

            managementCart.addFood(food)
        }
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

    private fun getResourceId(ctx: Context, name: String): Int {
        return ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
    }

    private fun setImg(ctx: Context, name: String, img: ImageView) {
        Glide.with(ctx).load(getResourceId(ctx, name)).into(img)
    }
}