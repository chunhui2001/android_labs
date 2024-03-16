package com.android_labs.criminal03constraintlayout

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

class CrimeCreateFragment: Fragment() {

    companion object {
        fun newInstance(): CrimeCreateFragment {
            return CrimeCreateFragment()
        }
    }

    private lateinit var crimeDataModel: CrimeDataModel

    private lateinit var fieldCrimeTitleEt: EditText
    private lateinit var fieldCrimeDateBtn: Button
    private lateinit var fieldCrimeSolvedCbx: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.crimeDataModel = CrimeDataModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.fieldCrimeTitleEt = view.findViewById(R.id.field_crime_title_et)
        this.fieldCrimeDateBtn = view.findViewById(R.id.field_crime_date_btn)
        this.fieldCrimeSolvedCbx = view.findViewById(R.id.field_crime_solved_cbx)

        this.fieldCrimeDateBtn.apply {
            text = crimeDataModel.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        this.fieldCrimeTitleEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crimeDataModel.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        this.fieldCrimeSolvedCbx.setOnCheckedChangeListener { _, isChecked ->
            crimeDataModel.solved = isChecked
        }
    }

}