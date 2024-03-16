package com.android_labs.criminal08menu

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

private const val DATE_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss"
private const val ARG_CURRENT_DATE = "com.android_labs.criminal08menu::current_date"

class DatePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
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
            return SimpleDateFormat(DATE_FORMATTER_PATTERN).format(date)
        }

        fun fromDateString(date: String): Date {
            return SimpleDateFormat(DATE_FORMATTER_PATTERN).parse(date)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            targetFragment?.let { fragment ->
                var date = GregorianCalendar(year, month, dayOfMonth).time
                val callback = fragment as Callbacks
                callback.onDateSelected(date)
            }
        }

        val calendar = Calendar.getInstance()

        arguments?.let {
            calendar.time = it.getSerializable(ARG_CURRENT_DATE) as Date
        }

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        return  DatePickerDialog(
            requireContext(), listener, year,month,day
        )
    }
}