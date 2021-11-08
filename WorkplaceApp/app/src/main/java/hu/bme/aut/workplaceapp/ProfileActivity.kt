package hu.bme.aut.workplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.workplaceapp.adapter.ProfilePageAdapter
import hu.bme.aut.workplaceapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        binding.vpProfile.adapter = ProfilePageAdapter(supportFragmentManager)

        setContentView(binding.root)
    }
}
