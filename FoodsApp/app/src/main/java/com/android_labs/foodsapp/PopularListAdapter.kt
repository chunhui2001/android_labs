package com.android_labs.foodsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PopularListAdapter(private var list: List<FoodModel>, var callbacks: Callbacks?): RecyclerView.Adapter<PopularListAdapter.ViewHolder>(){

    interface Callbacks {
        fun onFoodSelected(food: FoodModel)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val titleTxt = view.findViewById<TextView>(R.id.foodTitle)
        private val foodPic = view.findViewById<ImageView>(R.id.foodPic)
        private val feeTxt = view.findViewById<TextView>(R.id.feeTxt)
        private val addToCartBtn = view.findViewById<TextView>(R.id.addToCartBtn)

        fun bind(model: FoodModel) {
            this.titleTxt.text = model.title
            this.feeTxt.text = model.fee.toString()

            setImg(view, model.pic, this.foodPic)

            this.addToCartBtn.setOnClickListener {
                callbacks?.onFoodSelected(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_popular, parent, false))
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