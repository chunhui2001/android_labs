package com.android_labs.foodsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.math.RoundingMode

class CartListAdapter(private var list: List<FoodModel>, var callbacks: CartListAdapter.Callbacks?): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    interface Callbacks {
        fun onCountChanged(foodName: String, currentCount: Int)
    }

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {
        private var foodName = view.findViewById<TextView>(R.id.foodName)
        private var foodPic = view.findViewById<ImageView>(R.id.foodPic)

        private var foodPrice = view.findViewById<TextView>(R.id.foodPrice)
        private var totalMoney = view.findViewById<TextView>(R.id.totalMoney)

        private var minuxCountBtn = view.findViewById<ImageView>(R.id.minuxCountBtn)
        private var plusCountBtn = view.findViewById<ImageView>(R.id.plusCountBtn)

        private var currCount = view.findViewById<TextView>(R.id.currCount)

        private lateinit var currFood: FoodModel

        init {
            this.minuxCountBtn.setOnClickListener {
                callbacks?.onCountChanged(currFood.title, currFood.numberInCart - 1)
            }

            this.plusCountBtn.setOnClickListener {
                callbacks?.onCountChanged(currFood.title, currFood.numberInCart + 1)
            }
        }

        fun bind(food: FoodModel) {
            currFood = food

            this.foodName.text = food.title
            this.foodPrice.text = "$" + food.fee.toString()
            this.currCount.text = food.numberInCart.toString()
            this.totalMoney.text = "$" + (food.numberInCart * food.fee).toBigDecimal().setScale(2, RoundingMode.DOWN)

            setImg(view, food.pic, this.foodPic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.list[position])
    }

    private fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }

    private fun setImg(view: View, name: String, img: ImageView) {
        Glide.with(view.context).load(getResourceId(view, name)).into(img)
    }
}