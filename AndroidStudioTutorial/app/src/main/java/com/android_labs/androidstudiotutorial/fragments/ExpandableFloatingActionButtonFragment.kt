package com.android_labs.androidstudiotutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpandableFloatingActionButtonFragment: Fragment() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }

    private lateinit var plusBtn: FloatingActionButton
    private lateinit var penBtn: FloatingActionButton
    private lateinit var imgBtn: FloatingActionButton

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fab, container, false);

        this.plusBtn = view.findViewById(R.id.plusBtn)
        this.penBtn = view.findViewById(R.id.penBtn)
        this.imgBtn = view.findViewById(R.id.imgBtn)

        this.plusBtn.setOnClickListener {

            this.clicked = !this.clicked

            if (this.clicked) {
                this.penBtn.visibility = View.VISIBLE
                this.imgBtn.visibility = View.VISIBLE

                this.penBtn.startAnimation(fromBottom)
                this.imgBtn.startAnimation(fromBottom)
                this.plusBtn.startAnimation(rotateOpen)
            } else {
                this.penBtn.visibility = View.INVISIBLE
                this.imgBtn.visibility = View.INVISIBLE

                this.penBtn.startAnimation(toBottom)
                this.imgBtn.startAnimation(toBottom)
                this.plusBtn.startAnimation(rotateClose)
            }
        }

        this.penBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Pen Button clicked!", Toast.LENGTH_SHORT).show()
        }

        this.imgBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Img Button clicked!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}