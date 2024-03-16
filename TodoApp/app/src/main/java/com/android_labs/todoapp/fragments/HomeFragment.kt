package com.android_labs.todoapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.todoapp.R
import com.android_labs.todoapp.activities.LoginActivity
import com.android_labs.todoapp.activities.MainActivity
import com.android_labs.todoapp.adapters.TodoAdapter
import com.android_labs.todoapp.models.TodoModel
import com.android_labs.todoapp.services.API_URL_USER_DEFAULT_IMG
import com.android_labs.todoapp.services.API_URL_USER_HEAD_IMG
import com.android_labs.todoapp.services.TodoService
import com.android_labs.todoapp.services.UserService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.Instant

class HomeFragment : Fragment(), TodoAdapter.Callback {

    private lateinit var userService: UserService
    private lateinit var todoService: TodoService

    private lateinit var addTaskFab: FloatingActionButton

    private lateinit var listView: RecyclerView
    private lateinit var progressBar3: ProgressBar
    private lateinit var textView3: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        this.userService = UserService(this@HomeFragment.requireContext())
        this.todoService = TodoService(this@HomeFragment.requireContext())

        this.addTaskFab = view.findViewById(R.id.addTaskBtn)
        this.progressBar3 = view.findViewById(R.id.progressBar3)
        this.textView3 = view.findViewById(R.id.textView3)

        this.listView = view.findViewById(R.id.listView)
        this.listView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        this.addTaskFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

        this.addTaskFab.setOnClickListener {
            showAddTodoTaskDialog();
        }

        if (!this.userService.isLogin()) {
            this.textView3.text = "Please login!!!";
            this.textView3.visibility = View.VISIBLE
            this.progressBar3.visibility = View.INVISIBLE
        } else {
            loadData();
        }

        return view
    }

    private fun loadData() {
        this.progressBar3.visibility = View.VISIBLE
        this.textView3.visibility = View.INVISIBLE

        this.todoService.list(this.userService.userToken()) {todoList ->
            if (todoList.isEmpty()) {
                this.textView3.visibility = View.VISIBLE
            } else {
                this.textView3.visibility = View.INVISIBLE
            }

            this.progressBar3.visibility = View.INVISIBLE
            this.listView.adapter = TodoAdapter(todoList, this@HomeFragment);
        }
    }

    private fun showAddTodoTaskDialog() {
        if (!this.userService.isLogin()) {
            Toast.makeText(requireContext(), "Please login", Toast.LENGTH_SHORT).show();
        } else {
            var inflater = layoutInflater;
            var view = inflater.inflate(R.layout.todo_add_dialog, null);

            var todoTaskTitle = view.findViewById<EditText>(R.id.todoTaskTitle)
            var todoTaskDesc = view.findViewById<EditText>(R.id.todoTaskDesc)

            var dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(view)
                .setTitle("Add Todo Task")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

            dialog.setOnShowListener {
                var button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

                button.setOnClickListener {
                    var title = todoTaskTitle.text.toString();
                    var desc = todoTaskDesc.text.toString();

                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(requireContext(), "Please input Todo title", Toast.LENGTH_SHORT).show();
                    } else {
                        this@HomeFragment.todoService.save(
                            this@HomeFragment.userService.userToken(), title, desc
                        ) {
                            Log.d("setOnClickListener", "it=$it")
                            Toast.makeText(requireContext(), "Todo task saved.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadData();
                        }
                    }
                }
            }

            dialog.show()
        }
    }

    override fun onUpdate(model: TodoModel, onCompleted: (Boolean) -> Unit) {
        todoService.update(this.userService.userToken(), model) {
            onCompleted(it)
        }
    }

    override fun onDelete(model: TodoModel, adapterPosition: Int) {
        todoService.delete(this.userService.userToken(), model.id) {
            if (it) {
                Toast.makeText(requireContext(), "Deleted: ${model.title}, position=$adapterPosition", Toast.LENGTH_SHORT).show()
//                loadData();
//                this@HomeFragment.listView.removeItemDecorationAt(adapterPosition)
            }
        }
    }
}