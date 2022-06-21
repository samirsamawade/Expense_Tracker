package com.samawade.expensetracker.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.ui.auth.LoginFragment
import com.samawade.expensetracker.ui.base.BaseFragment
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val transactionType = listOf("income", "expense")


fun <A : Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean){
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f
}

//fun View.dialog(){
//
//}

fun View.snackbr(message: String, action: (()-> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (()-> Unit)? = null
){
    when{
        failure.isNetworkError -> requireView().snackbr("Please check your internet connection ", retry)
        failure.errorCode == 401 -> {
            if (this is LoginFragment){
                requireView().snackbr("Incorrect username or password ")
            }
            else {
                (this as BaseFragment<*,*,*>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbr(error)
        }
    }

}


fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    maxDate: Date? = null
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            myCalendar
                .get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

// dollar converter
fun dollar(amount: Double): String {

    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("USD")
    return format.format(amount)
}

val String.cleanTextContent: String
    get() {
        // strips off all non-ASCII characters
        var text = this
        text = text.replace("[^\\x00-\\x7F]".toRegex(), "")

        // erases all the ASCII control characters
        text = text.replace("[\\p{Cntrl}&&[^\r\n\t]]".toRegex(), "")

        // removes non-printable characters from Unicode
        text = text.replace("\\p{C}".toRegex(), "")
        text = text.replace(",".toRegex(), "")
        return text.trim()
    }


