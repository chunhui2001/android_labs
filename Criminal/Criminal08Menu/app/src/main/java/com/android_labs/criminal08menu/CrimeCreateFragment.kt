package com.android_labs.criminal08menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.Date

private const val DATE_PICKER_TAG = ""
private const val DATE_PICKER_CODE = 0

class CrimeCreateFragment: Fragment(), DatePickerFragment.Callbacks {

    companion object {
        fun newInstance(): CrimeCreateFragment {
            return CrimeCreateFragment()
        }
    }

    private lateinit var crimeTitleFieldEt: EditText
    private lateinit var crimeDateFieldBtn: Button
    private lateinit var crimeIsSolvedFieldCbx: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.crimeTitleFieldEt = view.findViewById(R.id.field_crime_title_et)
        this.crimeDateFieldBtn = view.findViewById(R.id.field_crime_date_btn)
        this.crimeIsSolvedFieldCbx = view.findViewById(R.id.field_crime_is_solved_cbx)

        this.crimeDateFieldBtn.apply {
            text = DatePickerFragment.toDateString(Date())

            setOnClickListener{_: View ->
                DatePickerFragment.newInstance(getCrimeDate()).apply {
                    setTargetFragment(this@CrimeCreateFragment, DATE_PICKER_CODE)
                    show(this@CrimeCreateFragment.requireFragmentManager(), DATE_PICKER_TAG)
                }
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDateSelected(date: Date) {
        this.crimeDateFieldBtn.text = DatePickerFragment.toDateString(date)
    }

    private fun getCrimeDate(): String {
        return this.crimeDateFieldBtn.text.toString()
    }
}