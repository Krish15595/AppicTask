package com.d2k.appictask.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import java.util.regex.Pattern

class ValidatorUtils {
    companion object{
        fun showToast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

         fun isNullOrEmpty(input: String?): Boolean = input == null || input.isEmpty()

         fun isValidEmail(context: Context, email: String?): Boolean {
            when {
                isNullOrEmpty(email) -> showToast(context, "Please enter Email.")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showToast(context, "Please enter a valid Email address.")
                else -> return true
            }
            return false
        }

         fun isValidMobile(context: Context, mobile: String?, regex: String = "^[0-9]{10}$"): Boolean {
             when {
                 isNullOrEmpty(mobile) -> showToast(context, "Please enter Mobile number.")
                 !Pattern.matches(regex, mobile) -> showToast(
                     context,
                     "Please enter a valid Mobile number."
                 )
                 else -> return true
             }
             return false
         }
    }
}