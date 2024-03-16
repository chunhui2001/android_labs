package com.android_labs.criminal05navigation

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

private const val ARG_CRIME_ID = "com.android_labs.criminal05navigation::crime_id"

class CrimeCreateFragment: Fragment() {

    companion object {
        fun newInstance(crimeId: UUID): CrimeCreateFragment {
            return CrimeCreateFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CRIME_ID, crimeId)
                }
            }
        }
    }

    private lateinit var crimeEntity: CrimeEntity

    private lateinit var etCrimeTitleField: EditText
    private lateinit var btnCrimeDateField: Button
    private lateinit var cbxCrimeIsSolved: CheckBox

    private val crimeDetailsViewModel: CrimeDetailsViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crimeEntity = CrimeEntity()
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeDetailsViewModel.getCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.etCrimeTitleField = view.findViewById(R.id.crime_title_field_et)
        this.btnCrimeDateField = view.findViewById(R.id.crime_date_field_btn)
        this.cbxCrimeIsSolved = view.findViewById(R.id.crime_solved_field_cbx)

        this.btnCrimeDateField.apply {
            text = crimeEntity.date.toString()
            isEnabled = false
        }

        this.etCrimeTitleField.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crimeEntity.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        this.cbxCrimeIsSolved.setOnCheckedChangeListener { _, isChecked ->
            crimeEntity.isSolved = isChecked
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeDetailsViewModel.crimeLiveData.observe(viewLifecycleOwner, Observer { crime ->
            crime?.let {
                crimeEntity = it
                this.etCrimeTitleField.setText(crimeEntity.title)
                this.btnCrimeDateField.text = crimeEntity.date.toString()
                this.cbxCrimeIsSolved.isChecked = crimeEntity.isSolved
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }
}