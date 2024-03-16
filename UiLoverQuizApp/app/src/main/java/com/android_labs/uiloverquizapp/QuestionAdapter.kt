package com.android_labs.uiloverquizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.uiloverquizapp.databinding.ViewholderQuestionBinding

class QuestionAdapter(
    private val correctAnswer: String,
    private val users: MutableList<String> = mutableListOf(),
    private var returnScore: Score,
): RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    interface Score {
        fun amount(number: Int, clickedAnser: String)
    }

    private val differCallback = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback);
    private lateinit var binding: ViewholderQuestionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context);
        binding = ViewholderQuestionBinding.inflate(
            inflater, parent, false
        )

        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var binding = ViewholderQuestionBinding.bind(holder.itemView)
        binding.answerTxt.text = differ.currentList[position]

        var correctPos = 0

        when(correctAnswer) {
            "a" -> correctPos = 0
            "b" -> correctPos = 1
            "c" -> correctPos = 2
            "d" -> correctPos = 3
        }

        if (position == 4) {
            binding.root.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            var str = ""

            when (position) {
                0 -> "a"
                1 -> "b"
                2 -> "c"
                3 -> "d"
            }

            users.add(4, str)

            notifyDataSetChanged()

            if (correctPos == position) {
                binding.answerTxt.setBackgroundResource(R.drawable.green_background)
                binding.answerTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.tick)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(5, str)
            } else {
                binding.answerTxt.setBackgroundResource(R.drawable.red_background)
                binding.answerTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.thieves)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(0, str)
            }
        }

        if (differ.currentList.size == 5) {
            holder.itemView.setOnClickListener(null)
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

    }
}