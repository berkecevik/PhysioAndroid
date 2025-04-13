package com.example.physiobuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.physiobuddy.databinding.FragmentLoginBinding
import com.example.physiobuddy.models.LoginRequest
import com.example.physiobuddy.api.RetrofitInstance
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val request = LoginRequest(email, password)
                    try {
                        val response = RetrofitInstance.api.login(request)
                        if (response.isSuccessful) {
                            val token = response.body()?.access_token
                            Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        } else {
                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding.goToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}