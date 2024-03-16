package com.android_labs.foodsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryListAdapter(private var list: List<CategoryModel>): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var titleTxt: TextView = view.findViewById(R.id.cateTitle)
        private var picImg: ImageView = view.findViewById(R.id.catePic)
        private var mainLayout: ConstraintLayout = view.findViewById(R.id.mainLayout)

        fun bind(model: CategoryModel, position: Int) {
            this.titleTxt.text = model.title
            setImg(view, model.pic, this.picImg)

            when(position) {
                0 -> {
//                    val layoutParams= this.mainLayout.layoutParams as RecyclerView.LayoutParams
//                    layoutParams.marginStart=view.context.resources.getDimension(0).toInt()
//
//                    this.mainLayout.layoutParams = layoutParams
                    this.mainLayout.background = ContextCompat.getDrawable(view.context, R.drawable.cat_background1)
                }
                1 -> {
                    this.mainLayout.background = ContextCompat.getDrawable(view.context, R.drawable.cat_background2)
                }
                2 -> {
                    this.mainLayout.background = ContextCompat.getDrawable(view.context, R.drawable.cat_background3)
                }
                3 -> {
                    this.mainLayout.background = ContextCompat.getDrawable(view.context, R.drawable.cat_background4)
                }
                4 -> {
                    this.mainLayout.background = ContextCompat.getDrawable(view.context, R.drawable.cat_background5)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {
        holder.bind(this.list[position], position)
    }

    private fun getResourceId(view: View, name: String): Int {
        return view.resources.getIdentifier(name, "drawable", view.context.packageName)
    }

    private fun setImg(view: View, name: String, img: ImageView) {
        Glide.with(view.context).load(getResourceId(view, name)).into(img)
    }
}