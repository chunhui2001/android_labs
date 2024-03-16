package com.android_labs.criminal09intentsend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.Date
import android.provider.ContactsContract

private const val DATE_PICKER_TAG = "com.android_labs.criminal09intentsend::date_picker_tag"
private const val DATE_PICKER_CODE = 0
private const val CHOOSE_CONTRACT_CODE = 1

class CrimeCreateFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var fieldCrimeTitleEt: EditText
    private lateinit var fieldCrimeDateBtn: Button
    private lateinit var fieldCrimeIsSolvedCbx: CheckBox

    private lateinit var btnChooseSuspect: Button
    private lateinit var btnSendReport: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.fieldCrimeTitleEt = view.findViewById(R.id.field_crime_title_et)
        this.fieldCrimeDateBtn = view.findViewById(R.id.field_crime_date_btn)
        this.fieldCrimeIsSolvedCbx = view.findViewById(R.id.field_crime_is_solved_cbx)

        this.btnChooseSuspect = view.findViewById(R.id.btn_choose_suspect)
        this.btnSendReport = view.findViewById(R.id.btn_send_report_message)
        this.fieldCrimeDateBtn.text = DatePickerFragment.toDateString(Date())

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.fieldCrimeDateBtn.setOnClickListener { _: View ->
            DatePickerFragment.newInstance(getCrimeDate()).apply {
                setTargetFragment(this@CrimeCreateFragment, DATE_PICKER_CODE)
                show(this@CrimeCreateFragment.requireFragmentManager(), DATE_PICKER_TAG)
            }
        }

        this.btnSendReport.setOnClickListener { _: View ->
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
                putExtra(Intent.EXTRA_TEXT, getCrimeReport())
            }.also { intent ->
                startActivity(intent)
            }
        }

        this.btnChooseSuspect.apply {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            setOnClickListener { _: View ->
                startActivityForResult(intent, CHOOSE_CONTRACT_CODE)
            }
        }
    }

    private fun getCrimeDate(): String {
        return this.fieldCrimeDateBtn.text.toString()
    }

    override fun onDateSelected(date: String) {
        this.fieldCrimeDateBtn.text = date
    }

    private fun getCrimeReport(): String {
        val solvedString = if (this.fieldCrimeIsSolvedCbx.isChecked) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }

        val dateString = this.fieldCrimeDateBtn.text.toString()

//        val suspect = if (crime.suspect.isBlank()) {
        val suspect = getString(R.string.crime_report_no_suspect)
//        } else {
//            getString(R.string.crime_report_suspect, crime.suspect)
//        }

        return getString(
            R.string.crime_report,
            this.fieldCrimeTitleEt.text.toString(), dateString, solvedString, suspect
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == CHOOSE_CONTRACT_CODE) {

        }
    }
}