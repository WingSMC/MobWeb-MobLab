package hu.bme.aut.workplaceapp

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import hu.bme.aut.workplaceapp.adapter.PaymentPageAdapter
import hu.bme.aut.workplaceapp.databinding.ActivityPaymentBinding

class PaymentActivity : FragmentActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        binding.vpPayment.adapter = PaymentPageAdapter(this)

        setContentView(binding.root)
    }
}
