package com.android_labs.criminal07dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_CRIME_DATE = "com.android_labs.criminal07dialog::crime_date"

class DatePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            return DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CRIME_DATE, date)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DatePickerDialog.OnDateSetListener{_, year, month, day ->
            var date = GregorianCalendar(year, month, day).time

            targetFragment?.let { fragment ->
                val callback = fragment as Callbacks
                callback.onDateSelected(date)
            }
        }

        val calendar = Calendar.getInstance()

        var date = arguments?.getSerializable(ARG_CRIME_DATE) as Date

        if (date != null) {
            calendar.time = date
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(), listener, year, month,day
        )
    }
}