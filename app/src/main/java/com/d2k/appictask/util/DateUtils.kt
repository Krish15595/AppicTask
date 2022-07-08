package com.d2k.appictask.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.widget.EditText
import com.d2k.appictask.R
import com.google.android.material.textfield.TextInputEditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateUtils {
    //Converting String to com.customer.kisanseordermanagement.ui.login.Data...
    companion object {
        val MONTH_FORMAT = "dd MMM yyyy"
        val DMY_FORMAT = "dd/MM/yyyy"
        val DASH_FORMAT = "dd-MM-yyyy"
        val MONTH_AND_TIME_FORMAT = "dd-MM-yyyy / hh:mm aa"

        val YMD_FORMAT = "yyyy-MM-dd"

        fun dateFormatter(date: String): Date? {
            var d: Date? = null
            val formatter = SimpleDateFormat(DMY_FORMAT)

            try {
                d = formatter.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return d
        }

        fun dateTimeConverter(date: String): String? {
            var d: Date? = null
            var dateTime: String? = null
            val formatter = SimpleDateFormat(YMD_FORMAT)

            try {
                d = formatter.parse(date)
                dateTime = convertedDate(d, 3)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return dateTime
        }

        //Converting date into specified format...
        fun convertedDate(date: Date?, formatType: Int): String? {
            if (date == null) {
                return "dd/MM/yyyy" //go to drive//////// is coming in to pull
            }
            var auditDate: String? = null
            if (formatType == 0) {
                val formatter = SimpleDateFormat(MONTH_FORMAT)
                auditDate = formatter.format(date)
                //return auditDate;
            } else if (formatType == 1) {
                val formatter = SimpleDateFormat(DMY_FORMAT)
                auditDate = formatter.format(date)
            } else if (formatType == 2) {
                val formatter = SimpleDateFormat(DASH_FORMAT)
                auditDate = formatter.format(date)
            } else if (formatType == 3) {
                val formatter =
                    SimpleDateFormat(MONTH_AND_TIME_FORMAT)
                auditDate = formatter.format(date)
            } else if (formatType == 4) {
                val formatter =
                    SimpleDateFormat(YMD_FORMAT)
                auditDate = formatter.format(date)
            }
            return auditDate
        }

        fun datePickerDialog(
            context: Context,
            editText: TextInputEditText,
            minDate: String? = null,
            maxDate: String? = null,
        ) {
            val calendar: Calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month: Int = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(
                    context, R.style.datepicker,
                    OnDateSetListener { _, year, month, dayOfMonth ->
                        val date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                        val date1: Date? = dateFormatter(date)
                        editText.setText(convertedDate(date1, 1))
                    }, year, month, day
                )
            if (minDate != null) {
                if (minDate == "today") {
                    datePickerDialog.datePicker.minDate = Date().getTime()
                } else {
                    datePickerDialog.datePicker.minDate = dateFormatter(minDate)!!.getTime()
                }
            }
            if (maxDate != null) {
                if (maxDate == "today") {
                    datePickerDialog.datePicker.maxDate = Date().getTime()
                } else if (maxDate == "major") {
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.YEAR, -18)
                    val date = cal.time
                    datePickerDialog.datePicker.maxDate = date.time
                } else {
                    datePickerDialog.datePicker.maxDate = dateFormatter(maxDate)!!.getTime()
                }
            }
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(context.resources.getColor(R.color.colorPrimary))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(context.resources.getColor(R.color.colorPrimary))
        }

        fun selectDob(context: Context?, editText: EditText) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -18)
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]

            val date = calendar.time
            val datePickerDialog =
                DatePickerDialog(
                    context!!, R.style.datepicker,
                    OnDateSetListener { _, year, month, dayOfMonth ->
                        val date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                        val date1 = dateFormatter(date)
                        editText.setText(convertedDate(date1, 4))
                    }, year, month, day
                )
            datePickerDialog.datePicker.maxDate = date.time
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(context.resources.getColor(R.color.colorPrimary))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(context.resources.getColor(R.color.colorPrimary))
        }
    }


}