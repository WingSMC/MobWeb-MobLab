package hu.bme.aut.workplaceapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.workplaceapp.fragments.MonthlyPaymentFragment
import hu.bme.aut.workplaceapp.fragments.PaymentTaxesFragment

class PaymentPageAdapter(fa: FragmentActivity):
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> PaymentTaxesFragment()
        1 -> MonthlyPaymentFragment()
        else -> PaymentTaxesFragment()
    }

    companion object {
        const val NUM_PAGES = 2
    }
}
