package com.android_labs.criminal09intentsend

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_CURRENT_DATE = "com.android_labs.criminal09intentsend::current_date"

class DatePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: String)
    }

    companion object {
        fun newInstance(date: String): DatePickerFragment {
            return DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CURRENT_DATE, fromDateString(date))
                }
            }
        }

        fun toDateString(date: Date): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        }

        fun fromDateString(date: String): Date {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            targetFragment?.let { fragment: Fragment ->
                var date = GregorianCalendar(year, month, dayOfMonth).time
                (fragment as Callbacks).onDateSelected(toDateString(date))
            }
        }

        val calendar = Calendar.getInstance()

        arguments?.let {
            calendar.time = it.getSerializable(ARG_CURRENT_DATE) as Date
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(), listener, year, month, day
        )
    }
}