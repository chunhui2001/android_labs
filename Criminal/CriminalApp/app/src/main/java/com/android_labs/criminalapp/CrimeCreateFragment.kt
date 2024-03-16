package com.android_labs.criminalapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

private const val ARG_CRIME_ID = "com.android_labs.criminalapp::crime_id"

class CrimeCreateFragment: Fragment() {

    companion object{
        fun newInstance(crimeId: UUID): CrimeCreateFragment {
            return CrimeCreateFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CRIME_ID, crimeId)
                }
            }
        }
    }

    private lateinit var currentCrime: CrimeDataModel

    private lateinit var fieldCrimeTitleEt: EditText
    private lateinit var fieldCrimeDateBtn: Button
    private lateinit var fieldCrimeIsSolvedCbx: CheckBox

    private val crimeDetailsViewModel: CrimeDetailsViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentCrime = CrimeDataModel()
        crimeDetailsViewModel.loadCrimeById(arguments?.getSerializable(ARG_CRIME_ID) as UUID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.fieldCrimeTitleEt = view.findViewById(R.id.crime_title_field)
        this.fieldCrimeDateBtn = view.findViewById(R.id.crime_date_field)
        this.fieldCrimeIsSolvedCbx = view.findViewById(R.id.crime_is_solved_field)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.fieldCrimeTitleEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentCrime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        this.fieldCrimeIsSolvedCbx.setOnCheckedChangeListener { _, isChecked ->
            currentCrime.isSolved = isChecked
        }

        this.crimeDetailsViewModel.currentCrime.observe(viewLifecycleOwner, Observer { crime ->
            this.fieldCrimeTitleEt.setText(crime.title)
            this.fieldCrimeDateBtn.text = crime.date.toString()
            this.fieldCrimeIsSolvedCbx.isChecked = crime.isSolved
        })
    }

    override fun onStart() {
        super.onStart()
    }
}