package com.android_labs.crime02recyclerview

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

    private lateinit var etxCrimeTitleField: EditText
    private lateinit var btnCrimeDateField: Button
    private lateinit var cbxCrimeSolvedField: CheckBox

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

        this.etxCrimeTitleField = view.findViewById(R.id.et_crime_title_field)
        this.btnCrimeDateField = view.findViewById(R.id.btn_crime_date_field)
        this.cbxCrimeSolvedField = view.findViewById(R.id.cbx_crime_solved_field)

        this.btnCrimeDateField.apply {
            text = crimeDataModel.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        this.etxCrimeTitleField.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crimeDataModel.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        this.cbxCrimeSolvedField.setOnCheckedChangeListener { _, isChecked ->
            crimeDataModel.solvered = isChecked
        }
    }
}