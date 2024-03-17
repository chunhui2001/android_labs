package com.android_labs.androidstudiotutorial.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.app.Dialog
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R

class CountDownTimerFragment : Fragment() {

    private var isStart: Boolean = false
    private var timerCountDown: CountDownTimer? = null
    private val defaultValue = 100

    private lateinit var progressBar1: ProgressBar
    private lateinit var progressBar2: ProgressBar

    private lateinit var timerLeftTxt: TextView
    private lateinit var timerEditBtn: ImageView
    private lateinit var timerAddBtn: TextView
    private lateinit var timerResetBtn: ImageView
    private lateinit var timerStartBtn: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_countdown_timer, container, false)

        this.progressBar1 = view.findViewById(R.id.progressBar1)
        this.progressBar2 = view.findViewById(R.id.progressBar2)

        this.timerLeftTxt = view.findViewById(R.id.timerLeftTxt)
        this.timerEditBtn = view.findViewById(R.id.timerEditBtn)
        this.timerAddBtn = view.findViewById(R.id.timerAddBtn)
        this.timerResetBtn = view.findViewById(R.id.timerResetBtn)
        this.timerStartBtn = view.findViewById(R.id.timerStartBtn)

        this.timerEditBtn.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.timer_add_dialog)

            val timerSetInput = dialog.findViewById<EditText>(R.id.timerLeftTxtInput)
            val timerLeftAddOkBtn = dialog.findViewById<AppCompatButton>(R.id.timerLeftAddOkBtn)

            timerLeftAddOkBtn.setOnClickListener {
                if (timerSetInput.text.isNotEmpty()) {
                    this@CountDownTimerFragment.reset(timerSetInput.text.toString().toInt())
                    this@CountDownTimerFragment.startTimer()

                    dialog.dismiss()
                }
            }

            dialog.show()
        }

        this.timerStartBtn.setOnClickListener {
            if (this.progressBar1.max > 0 && !this.isStart) {
                this.startTimer()
            } else {
                if (this.isStart) {
                    this.timerStartBtn.text = "Start"
                    this.isStart = false
                    this.timerCountDown?.cancel()
                }
            }
        }

        this.timerResetBtn.setOnClickListener {
            this@CountDownTimerFragment.reset(defaultValue)
        }

        this.timerAddBtn.setOnClickListener {
            this.progressBar1.max = (this.progressBar1.max + 15 * 1000)
            this.progressBar2.max = this.progressBar1.max

            this.timerCountDown?.cancel()
            this.startTimer()
        }

        this.timerLeftTxt.text = defaultValue.toString()

        this.progressBar1.max = defaultValue * 1000
        this.progressBar2.max = this.progressBar1.max

        return view
    }

    private fun reset(timeCount: Int) {
        this@CountDownTimerFragment.timerCountDown?.cancel()

        this@CountDownTimerFragment.progressBar1.progress = 0
        this@CountDownTimerFragment.progressBar2.progress = 0

        this@CountDownTimerFragment.timerStartBtn.text = "Start"
        this@CountDownTimerFragment.isStart = false
        this.timerLeftTxt.text = timeCount.toString()

        this.progressBar1.max = timeCount * 1000
        this.progressBar2.max = this.progressBar1.max
    }

    private fun startTimer() {
        this.timerCountDown = object : CountDownTimer(
            (this.progressBar1.max).toLong() - this@CountDownTimerFragment.progressBar1.progress, 10
        ) {
            override fun onTick(millisUntilFinished: Long) {
                this@CountDownTimerFragment.progressBar1.progress += 1 * 100
                this@CountDownTimerFragment.progressBar2.progress = this@CountDownTimerFragment.progressBar1.progress

                this@CountDownTimerFragment.timerLeftTxt.text =
                    ((this@CountDownTimerFragment.progressBar1.max - this@CountDownTimerFragment.progressBar1.progress) / 1000).toString()

                if (this@CountDownTimerFragment.progressBar1.progress == this@CountDownTimerFragment.progressBar1.max) {
                    this@CountDownTimerFragment.progressBar1.progress = 0
                    this@CountDownTimerFragment.progressBar2.progress = 0

                    this@CountDownTimerFragment.timerStartBtn.text = "Start"
                    this@CountDownTimerFragment.isStart = false
                    this@CountDownTimerFragment.timerLeftTxt.text =
                        (this@CountDownTimerFragment.progressBar1.max / 1000).toString()

                    this@CountDownTimerFragment.timerCountDown?.cancel()

                    Toast.makeText(
                        this@CountDownTimerFragment.requireContext(),
                        "Times Up!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFinish() {

            }
        }

        this.timerCountDown?.start()
        this.isStart = true
        this.timerStartBtn.text = "Pause"
    }

    override fun onDestroy() {
        super.onDestroy()
        this.timerCountDown?.cancel()
    }
}