package hu.bme.aut.workplaceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.PaymentMonthlyBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class MonthlyPaymentFragment: Fragment() {
    private lateinit var binding: PaymentMonthlyBinding
    private val chartLabels = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PaymentMonthlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = binding.chartMonthlyPayment
        chart.description.isEnabled = false
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return chartLabels[value.toInt()]
            }
        }

        updateChart()
    }

    private fun updateChart() {
        val payments = DataManager.grossPayment
        val samples = ArrayList<BarEntry>(12)
        for(i in payments.indices) {
            samples.add(BarEntry(i.toFloat(), payments[i].toFloat()))
        }
        val chart = binding.chartMonthlyPayment
        val dataSet = BarDataSet(samples, "Monthly Payments")
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        dataSet.valueTextSize = 15f
        chart.data = BarData(dataSet)
        chart.invalidate()
    }
}
