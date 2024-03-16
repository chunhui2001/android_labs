package com.android_labs.criminal10intent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_CURRENT = "com.android_labs.criminal10intent::current_date"

class DatePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            return DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CURRENT, date)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            targetFragment?.let { fragment ->
                var date = GregorianCalendar(year, month, dayOfMonth).time
                val callbacks = fragment as Callbacks
                callbacks.onDateSelected(date)
            }
        }

        var calendar = Calendar.getInstance()

        arguments?.let { it ->
            it.getSerializable(ARG_CURRENT)?.let { it
                calendar.time = it as Date
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(), listener, year, month,day
        )
    }
}