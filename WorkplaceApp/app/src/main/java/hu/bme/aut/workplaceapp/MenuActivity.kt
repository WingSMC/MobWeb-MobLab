package hu.bme.aut.workplaceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.workplaceapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)


        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnHoliday.setOnClickListener {
            startActivity(Intent(this, HolidayActivity::class.java))
        }

        binding.btnPayment.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        setContentView(binding.root)
    }
}
