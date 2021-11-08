package hu.bme.aut.publictransport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.RadioGroup
import hu.bme.aut.publictransport.databinding.ActivityDetailsBinding
import java.util.*
import kotlin.math.roundToInt

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val KEY_TRANSPORT_TYPE = "KEY_TRANSPORT_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transportType = this.intent.getIntExtra(KEY_TRANSPORT_TYPE, -1)
        val currentDate = Calendar.getInstance().timeInMillis

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        binding.tvTicketType.text = getTypeString(transportType)
        binding.dpStartDate.minDate = currentDate
        binding.dpEndDate.minDate = currentDate

        val priceCalculator = {
            val startInMillis = datePickerGetMillis(binding.dpStartDate)
            val endInMillis = datePickerGetMillis(binding.dpEndDate)
            val nDays = ((endInMillis - startInMillis) / (1000 * 60 * 60 * 24)).toInt() + 1

            val dailyPrice = when (transportType) {
                ListActivity.TYPE_BIKE -> 700
                ListActivity.TYPE_BUS -> 1000
                ListActivity.TYPE_TRAIN -> 1500
                ListActivity.TYPE_BOAT -> 2500
                else -> 2500
            }

            val discount = when (binding.rgPriceCategory.checkedRadioButtonId) {
                binding.rbFullPrice.id -> 1.0
                binding.rbSenior.id -> 0.1
                binding.rbPublicServant.id -> 0.5
                else -> 1.0
            }

            binding.tvPrice.text = (nDays * dailyPrice * discount).roundToInt().toString()
        }

        binding.dpStartDate.setOnDateChangedListener { _, y, m, d ->
            binding.dpEndDate.minDate = GregorianCalendar(y, m, d).timeInMillis
            priceCalculator()
        }
        binding.dpEndDate.setOnDateChangedListener { _, _, _, _ -> priceCalculator() }
        binding.rgPriceCategory.setOnCheckedChangeListener { _: RadioGroup, _: Int -> priceCalculator() }
        binding.btnPurchase.setOnClickListener {
            val typeString = getTypeString(transportType)
            val dateString = getDateFrom(binding.dpStartDate) + " - " + getDateFrom(binding.dpEndDate)

            val intent = Intent(this, PassActivity::class.java)
            intent.putExtra(PassActivity.KEY_TYPE_STRING, typeString)
            intent.putExtra(PassActivity.KEY_DATE_STRING, dateString)
            startActivity(intent)
        }

        priceCalculator()
        setContentView(binding.root)
    }

    private fun getTypeString(transportType: Int): String {
        return when (transportType) {
            ListActivity.TYPE_BUS -> "Bus pass"
            ListActivity.TYPE_TRAIN -> "Train pass"
            ListActivity.TYPE_BIKE -> "Bike pass"
            ListActivity.TYPE_BOAT -> "Boat pass"
            else -> "Unknown pass type"
        }
    }

    private fun getDateFrom(picker: DatePicker): String {
        return String.format(
            Locale.getDefault(), "%04d.%02d.%02d.",
            picker.year, picker.month + 1, picker.dayOfMonth
        )
    }

    private fun datePickerGetMillis(dp: DatePicker): Long {
        with(dp) {
            return GregorianCalendar(this.year, this.month, this.dayOfMonth).timeInMillis
        }
    }
}
