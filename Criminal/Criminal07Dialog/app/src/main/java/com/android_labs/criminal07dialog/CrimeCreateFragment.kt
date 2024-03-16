package com.android_labs.criminal07dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

private const val REQUEST_CODE_DATE_PICKER = 0
private const val DATE_PICKER_DIALOG = "com.android_labs.criminal07dialog::date_picker_dialog"
private const val ARG_CRIME_ID = "com.android_labs.criminal07dialog::crime_id"
private const val DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss"

class CrimeCreateFragment: Fragment(), DatePickerFragment.Callbacks {

    companion object {
        fun newInstance(crimeId: UUID): CrimeCreateFragment {
            return CrimeCreateFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CRIME_ID, crimeId)
                }
            }
        }

        fun toDateString(date: Date): String {
            return SimpleDateFormat(DATE_FORMATTER).format(date)
        }
    }

    private lateinit var fieldCrimeTitleEt: EditText
    private lateinit var fieldCrimeDateBtn: Button
    private lateinit var fieldCrimeIsSolved: CheckBox

    private val crimeDetailsViewModel: CrimeDetailsViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            crimeDetailsViewModel.loadCrimeById(it.getSerializable(ARG_CRIME_ID) as UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.fieldCrimeTitleEt = view.findViewById(R.id.field_crime_title_et)
        this.fieldCrimeDateBtn = view.findViewById(R.id.field_crime_date_btn)
        this.fieldCrimeIsSolved = view.findViewById(R.id.field_crime_is_solved_cbx)

        this.fieldCrimeDateBtn.text = toDateString(Date())

        return view
    }

    override fun onStart() {
        super.onStart()

        this.fieldCrimeDateBtn.setOnClickListener{_: View ->

            DatePickerFragment.newInstance(Date()).apply {
                setTargetFragment(this@CrimeCreateFragment, REQUEST_CODE_DATE_PICKER)
                show(this@CrimeCreateFragment.requireFragmentManager(), DATE_PICKER_DIALOG)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeDetailsViewModel.currentCrimeLiveData.observe(viewLifecycleOwner, Observer { crime ->
            this.fieldCrimeTitleEt.setText(crime.title)
            this.fieldCrimeDateBtn.text = toDateString(crime.date)
            this.fieldCrimeIsSolved.isChecked = crime.isSolved
        })
    }

    override fun onDateSelected(date: Date) {
        this.fieldCrimeDateBtn.text = toDateString(date)
    }

    override fun onStop() {
        super.onStop()

        crimeDetailsViewModel.saveCrime(
            fieldCrimeTitleEt.text.toString(),
            fromDateString(this.fieldCrimeDateBtn.text.toString()),
            fieldCrimeIsSolved.isChecked
        )
    }

    private fun fromDateString(str: String): Date {
        return SimpleDateFormat(DATE_FORMATTER).parse(str)
    }
}