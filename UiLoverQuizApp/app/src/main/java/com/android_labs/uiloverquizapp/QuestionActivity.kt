package com.android_labs.uiloverquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.RoundedCorner
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android_labs.uiloverquizapp.databinding.ActivityQuestionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class QuestionActivity : AppCompatActivity(), QuestionAdapter.Score {

    private lateinit var binding: ActivityQuestionBinding

    private var position = 0
    private var receivedList: MutableList<QuestionModel> = mutableListOf()
    private var allScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val window: Window = this@QuestionActivity.window

        // 修改状态栏颜色
        window.statusBarColor = ContextCompat.getColor(this@QuestionActivity, R.color.grey)

        receivedList = intent.getParcelableArrayListExtra<QuestionModel>("list")!!.toMutableList()

        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }

            progressBar.progress = 1

            questionTxt.text =receivedList[position].question

            val drawableResourceId: Int = binding.root.resources.getIdentifier(
                receivedList[position].picPath, "drawable", binding.root.context.packageName
            )

            Glide.with(this@QuestionActivity)
                .load(drawableResourceId)
                .centerCrop().apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                .into(pic)

            loadAnswers()

            rightArrow.setOnClickListener {
                if (progressBar.progress == 10) {
                    val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
                    intent.putExtra("Score", allScore)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }

                position++
                progressBar.progress = progressBar.progress + 1
                questionNumberTxt.text = "Question " + progressBar.progress + "/10"
                questionTxt.text = receivedList[position].question

                val drawableResourceId: Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath, "drawable", binding.root.context.packageName
                )

                Glide.with(this@QuestionActivity)
                    .load(drawableResourceId)
                    .centerCrop().apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)

                loadAnswers()
            }

            leftArrow.setOnClickListener {
                if (progressBar.progress == 1) {
                    return@setOnClickListener
                }

                position--
                progressBar.progress = progressBar.progress - 1
                questionNumberTxt.text = "Question " + progressBar.progress + "/10"
                questionTxt.text = receivedList[position].question

                val drawableResourceId: Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath, "drawable", binding.root.context.packageName
                )

                Glide.with(this@QuestionActivity)
                    .load(drawableResourceId)
                    .centerCrop().apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)

                loadAnswers()
            }
        }
    }

    private fun loadAnswers() {
        val users: MutableList<String> = mutableListOf(
            receivedList[position].answer_1.toString(),
            receivedList[position].answer_2.toString(),
            receivedList[position].answer_3.toString(),
            receivedList[position].answer_4.toString()
        )

        if (receivedList[position].clickedAnswer != null) {
            users.add(receivedList[position].clickedAnswer.toString())
        }

        val questionAdapter by lazy {
            QuestionAdapter(
                receivedList[position].correctAnswer.toString(),
                users,
                this
            )
        }

        questionAdapter.differ.submitList(users)

        binding.questionView.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            adapter = questionAdapter
        }
    }

    override fun amount(number: Int, clickedAnser: String) {
        allScore += number
        receivedList[position].clickedAnswer = clickedAnser
    }
}