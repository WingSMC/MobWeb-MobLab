package hu.bme.aut.workplaceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.ProfileDetailBinding

class DetailsProfileFragment : Fragment() {
    private lateinit var binding: ProfileDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(DataManager.person) {
            binding.tvId.text = id
            binding.tvSSN.text = socialSecurityNumber
            binding.tvTaxId.text = taxId
            binding.tvRegistrationId.text = registrationId
        }
    }
}
