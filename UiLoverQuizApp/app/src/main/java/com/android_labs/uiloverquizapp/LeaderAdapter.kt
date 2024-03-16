package com.android_labs.uiloverquizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.uiloverquizapp.databinding.ViewholderLeadersBinding
import com.bumptech.glide.Glide

class LeaderAdapter(private val list: List<UserModel>) : RecyclerView.Adapter<LeaderAdapter.ViewHolder>() {

    override fun getItemCount(): Int { return list.size }

    override fun onBindViewHolder(holder: LeaderAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderAdapter.ViewHolder {
        val view = ViewholderLeadersBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(view)
    }

    inner class ViewHolder(private val binding: ViewholderLeadersBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserModel) {
            binding.textView15.text = model.id.toString();
            binding.textView18.text = model.name
            binding.textView19.text = model.score.toString()

            var drawableResourceId: Int = binding.root.resources.getIdentifier(
                model.pic,
                "drawable", binding.root.context.packageName
            )

            Glide.with(binding.root.context).load(drawableResourceId).into(binding.imageView1221)
        }
    }
}