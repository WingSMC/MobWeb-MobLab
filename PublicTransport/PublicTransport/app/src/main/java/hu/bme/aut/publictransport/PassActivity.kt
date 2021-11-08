package hu.bme.aut.publictransport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.publictransport.databinding.ActivityPassBinding

class PassActivity : AppCompatActivity() {
    companion object {
        const val KEY_DATE_STRING = "KEY_DATE_STRING"
        const val KEY_TYPE_STRING = "KEY_TYPE_STRING"
    }

    private lateinit var  binding: ActivityPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPassBinding.inflate(layoutInflater)
        binding.tvTicketType.text = intent.getStringExtra(KEY_TYPE_STRING)
        binding.tvDates.text = intent.getStringExtra(KEY_DATE_STRING)
        setContentView(binding.root)
    }
}