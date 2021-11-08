package hu.bme.aut.workplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.ActivityHolidayBinding
import hu.bme.aut.workplaceapp.fragments.DatePickerDialogFragment

class HolidayActivity : AppCompatActivity(), DatePickerDialogFragment.OnDateSelectedListener {
    private lateinit var binding: ActivityHolidayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidayBinding.inflate(layoutInflater)

        with(binding.btnTakeHoliday) {
            isEnabled = 0 < DataManager.remainingHolidays
            setOnClickListener {
                DatePickerDialogFragment().show(supportFragmentManager, "DATE_TAG")
            }
        }

        binding.chartHoliday.description.isEnabled = false

        loadHolidays()
        setContentView(binding.root)
    }

    private fun loadHolidays() {
        val samples = listOf(
            PieEntry(DataManager.holidays.toFloat(), "Taken"),
            PieEntry(DataManager.remainingHolidays.toFloat(), "Remaining")
        )
        val dataSet = PieDataSet(samples, "Holidays")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 20f
        with(binding.chartHoliday) {
            data = PieData(dataSet)
            invalidate()
        }
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        DataManager.holidays += 1
        binding.btnTakeHoliday.isEnabled = 0 < DataManager.remainingHolidays
        this.loadHolidays()
    }
}
