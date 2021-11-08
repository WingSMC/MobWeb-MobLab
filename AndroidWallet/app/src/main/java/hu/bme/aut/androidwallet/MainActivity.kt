package hu.bme.aut.androidwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.androidwallet.databinding.ActivityMainBinding
import hu.bme.aut.androidwallet.databinding.SalaryRowBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.expenseOrIncome.setOnCheckedChangeListener { _, b -> binding.salaryName.hint = if(b) "To" else "From" }

        binding.saveButton.setOnClickListener {
            if (binding.salaryName.text.toString().isEmpty() || binding.salaryAmount.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.warn_message, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val rowBinding = SalaryRowBinding.inflate(layoutInflater)
            val isExpense = binding.expenseOrIncome.isChecked
            val sumStr = binding.salaryAmount.text.toString()
            val sum = sumStr.toDouble()
            this.total += if (isExpense) -sum else sum

            rowBinding.salaryDirectionIcon.setImageResource(
                if (isExpense) R.drawable.expense
                else R.drawable.income
            )
            rowBinding.rowSalaryName.text = binding.salaryName.text.toString()
            rowBinding.rowSalaryAmount.text = "$sumStr Ft"

            binding.listOfRows.addView(rowBinding.root)
            binding.salaryName.setText("")
            binding.salaryAmount.setText("")
            binding.totalValue.text = total.toString()
            binding.totalRow.visibility = View.VISIBLE
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                binding.listOfRows.removeAllViews()
                binding.totalRow.visibility = View.GONE
                this.total = 0.0
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
