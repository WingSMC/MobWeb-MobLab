package hu.bme.aut.workplaceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.PaymentTaxesBinding

class PaymentTaxesFragment : Fragment() {
    private lateinit var binding: PaymentTaxesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PaymentTaxesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chartTaxes.description.isEnabled = false
        updateTaxChart()
    }

    private fun updateTaxChart() {
        val currentPayment: Double
        with(DataManager.grossPayment) {
            currentPayment = this[this.size - 1]
        }

        val totalTaxes = DataManager.taxes.sumOf { T -> T.amount }
        val samples = DataManager.taxes
            .map { tax ->
                PieEntry((tax.amount * currentPayment).toFloat(), tax.name)
            } + PieEntry((currentPayment * ( 1.0-totalTaxes)).toFloat(),"Net income")

        val dataSet = PieDataSet(samples, "Taxes")
        dataSet.colors = ColorTemplate.PASTEL_COLORS.toList()
        dataSet.valueTextSize = 20f

        with(binding.chartTaxes) {
            centerText = "$currentPayment Ft"
            setCenterTextSize(20f)
            data = PieData(dataSet)
            invalidate()
        }
    }
}
