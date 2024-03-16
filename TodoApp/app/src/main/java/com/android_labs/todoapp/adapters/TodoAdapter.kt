package com.android_labs.todoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.todoapp.R
import com.android_labs.todoapp.models.TodoModel
import com.android_labs.todoapp.services.UtilService

class TodoAdapter(private var list: MutableList<TodoModel>, private var cb: Callback) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    interface Callback {
        fun onUpdate(model: TodoModel, onCompleted: (Boolean) -> Unit)
        fun onDelete(model: TodoModel, adapterPosition: Int)
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var currModel: TodoModel

        private var accordianTitle = this.view.findViewById<CardView>(R.id.accordianTitle)
        private var todoBody = this.view.findViewById<RelativeLayout>(R.id.todoBody)

        private var todoTitle = this.view.findViewById<TextView>(R.id.todoTitle)
        private var todoDesc = this.view.findViewById<TextView>(R.id.todoDesc)
        private var todoDescEdit = this.view.findViewById<EditText>(R.id.todoDescEdit)

        private var slideDownBtn = this.view.findViewById<ImageView>(R.id.slideDownBtn)
        private var btnDelete = this.view.findViewById<ImageView>(R.id.btnDelete)
        private var btnEdit = this.view.findViewById<ImageView>(R.id.btnEdit)
        private var btnDone = this.view.findViewById<ImageView>(R.id.btnDone)

        init {
            var colors = this.view.resources.getIntArray(R.array.androidColors)
            var color = colors[java.util.Random().nextInt(colors.size)]

            this.accordianTitle.setBackgroundColor(color)

            this.btnDelete.setOnClickListener {
                cb.onDelete(this.currModel, bindingAdapterPosition)
                list.removeAt(bindingAdapterPosition)
                notifyDataSetChanged()
            }

            this.btnEdit.setOnClickListener {
                if (todoDescEdit.visibility != View.VISIBLE) {
                    this.todoDescEdit.visibility = View.VISIBLE
                    this.todoDesc.visibility = View.GONE
                    this.todoDescEdit.setText(this.currModel.description)
                }
            }

            this.btnDone.setOnClickListener {
                this.currModel.description = this.todoDescEdit.text.toString()

                cb.onUpdate(this.currModel) {
                    if (it) {
                        this.todoDescEdit.visibility = View.GONE
                        this.todoDesc.visibility = View.VISIBLE
                        this.todoBody.visibility = View.GONE
                        notifyDataSetChanged()
                    }
                }
            }

            this.slideDownBtn.setOnClickListener {
                if (this.todoBody.visibility == View.VISIBLE) {
                    this.todoBody.visibility = View.GONE
                } else {
                    this.todoBody.visibility = View.VISIBLE
                    this.todoDesc.visibility = View.VISIBLE
                }
            }
        }

        fun bind(model: TodoModel) {
            this.currModel = model
            this.todoTitle.text = model.title
            this.todoDesc.text = model.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_bar, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.list[position])
    }
}