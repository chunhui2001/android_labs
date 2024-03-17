package com.android_labs.androidstudiotutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

class PersistentBottomSheetFragment: Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    private lateinit var bottomSheetView: NestedScrollView

    private lateinit var buttonExpandBtn: Button
    private lateinit var buttonCollapseBtn: Button
    private lateinit var textViewStaus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        this.bottomSheetView = view.findViewById(R.id.bottomSheetView)

        this.buttonExpandBtn = view.findViewById(R.id.buttonExpandBtn)
        this.buttonCollapseBtn = view.findViewById(R.id.buttonCollapseBtn)
        this.textViewStaus = view.findViewById(R.id.textViewStaus)

        this.bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        this.buttonExpandBtn.setOnClickListener {
            this.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        this.buttonCollapseBtn.setOnClickListener {
            this.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        this.bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetCallback() {
            override fun onStateChanged(p0: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        this@PersistentBottomSheetFragment.textViewStaus.text = "Expanded"
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        this@PersistentBottomSheetFragment.textViewStaus.text = "Dragging"
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        this@PersistentBottomSheetFragment.textViewStaus.text = "Collapsed"
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        this@PersistentBottomSheetFragment.textViewStaus.text = "Hidden"
                    }

                    BottomSheetBehavior.STATE_SETTLING -> {
                        this@PersistentBottomSheetFragment.textViewStaus.text = "Setting"
                    }
                }
            }

            override fun onSlide(p0: View, p1: Float) {
                this@PersistentBottomSheetFragment.textViewStaus.text = "Sliding"
            }
        })

        return view
    }
}
