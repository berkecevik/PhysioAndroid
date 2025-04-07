package com.example.physiobuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.physiobuddy.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupDropdowns()

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullNameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val age = binding.ageInput.text.toString()
            val gender = binding.genderDropdown.selectedItem.toString()
            val disability = binding.disabilityDropdown.selectedItem.toString()
            val country = binding.countryDropdown.selectedItem.toString()
            val phone = binding.phoneInput.text.toString()

            // Validate required fields
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                age.isEmpty() || phone.isEmpty() ||
                gender == "Gender" || disability == "Disability Status" || country == "Country"
            ) {
                Toast.makeText(requireContext(), "Please fill all required fields correctly", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Perform registration using backend API
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        return binding.root
    }

    private fun setupDropdowns() {
        val genderAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.genderDropdown.adapter = genderAdapter

        val disabilityAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.disability_options,
            android.R.layout.simple_spinner_item
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.disabilityDropdown.adapter = disabilityAdapter

        val countryAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.country_options,
            android.R.layout.simple_spinner_item
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.countryDropdown.adapter = countryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
