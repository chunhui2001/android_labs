package com.android_labs.uiloverquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android_labs.uiloverquizapp.databinding.ActivityLeaderBinding
import com.bumptech.glide.Glide

class LeaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaderBinding.inflate(layoutInflater)

        // 绑定视图
        this.bindingView(loadDataTop())

        // 设置视图
        this.setContentView(binding.root)

        // 修改状态栏颜色
        this@LeaderActivity.window.statusBarColor = ContextCompat.getColor(this@LeaderActivity, R.color.grey)
    }

    private fun bindingView(data: MutableList<UserModel>) {

        binding.apply {
            textView1601.text = data[0].score.toString()
            textView1602.text = data[1].score.toString()
            textView1603.text = data[2].score.toString()

            titleTop1Txt.text = data[0].name
            titleTop2Txt.text = data[1].name
            titleTop3Txt.text = data[2].name

            Glide.with(root.context).load(root.resources.getIdentifier(
                data[0].pic,"drawable", root.context.packageName
            )).into(imageView1201)

            Glide.with(root.context).load(root.resources.getIdentifier(
                data[1].pic,"drawable", root.context.packageName
            )).into(imageView12002)

            Glide.with(root.context).load(root.resources.getIdentifier(
                data[2].pic,"drawable", root.context.packageName
            )).into(imageView12)

            bottomMenu.setItemSelected(R.id.board)

            bottomMenu.setOnItemSelectedListener {
                if (it == R.id.home) {
                    startActivity(Intent(this@LeaderActivity, MainActivity::class.java))
                }
            }

            leaderView.apply {
                layoutManager = LinearLayoutManager(this@LeaderActivity)
                adapter = LeaderAdapter(loadDataList())
            }
        }
    }

    private fun loadDataTop(): MutableList<UserModel> {
        return mutableListOf(
            UserModel(1, "Sophia", "person1", 4850),
            UserModel(2, "Daniel", "person2", 4560),
            UserModel(3, "James", "person3", 3873),
        )
    }

    private fun loadDataList(): MutableList<UserModel> {
        return mutableListOf(
            UserModel(4, "John Smith", "person4", 3250),
            UserModel(5, "Emily Johnson", "person5", 3015),
            UserModel(6, "David Brown", "person6", 2970),
            UserModel(7, "Sarah Wilson", "person7", 2870),
            UserModel(8, "Michael Davis", "person8", 2670),
            UserModel(9, "Sarah Wilson", "person9", 2380),
        )
    }
}