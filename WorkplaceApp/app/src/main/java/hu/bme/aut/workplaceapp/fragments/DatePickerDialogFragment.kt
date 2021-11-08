package hu.bme.aut.workplaceapp.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{
    private lateinit var onDateSelectedListener: OnDateSelectedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context !is OnDateSelectedListener){
            throw RuntimeException("The activity does not implement the OnDateSelectedListener interface")
        }
        onDateSelectedListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(requireContext(), this, year, month, day)
        datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis + (24*60*60*1000)
        return datePicker
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) =
        onDateSelectedListener.onDateSelected(year, month, dayOfMonth)

    interface OnDateSelectedListener {
        fun onDateSelected(year: Int, month: Int, day: Int)
    }
}
