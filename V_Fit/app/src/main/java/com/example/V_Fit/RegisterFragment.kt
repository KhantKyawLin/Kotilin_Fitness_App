package com.example.V_Fit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupAnimations()
        setupListeners()

        return binding.root
    }

    private fun setupAnimations() {
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val bottomDown = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_down)

        binding.topLinearLayout.animation = bottomDown

        Handler(Looper.getMainLooper()).postDelayed({
            binding.imageView.animation = fadeIn
            binding.textView.animation = fadeIn
            binding.cardView.animation = fadeIn
        }, 1000)
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            registerAction()
        }

        binding.login.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerAction() {
        val username = binding.txtUserName.text.toString().trim()
        val email = binding.txtUserEmail.text.toString().trim()
        val password = binding.txtPassword.text.toString()
        val confirmPassword = binding.txtConfirmPassword.text.toString()

        when {
            username.isEmpty() -> binding.txtUserName.error = "Username must be filled!"
            email.isEmpty() -> binding.txtUserEmail.error = "Email must be filled!"
            password.isEmpty() -> binding.txtPassword.error = "Password must be filled!"
            confirmPassword.isEmpty() -> binding.txtConfirmPassword.error = "Confirm Password must be filled!"
            password != confirmPassword -> {
                binding.txtPassword.error = "Passwords do not match!"
                binding.txtConfirmPassword.error = "Passwords do not match!"
            }
            else -> {
                val url = "http://10.0.2.2/V_Fit_API/registerUser.php"
                val queue = Volley.newRequestQueue(requireContext())

                val request = object : StringRequest(
                    Request.Method.POST, url,
                    { response ->
                        Toast.makeText(context, "Response: $response", Toast.LENGTH_LONG).show()
                        Log.d("Registration", "Success: $response")

                        // Navigate after successful registration
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(action)
                    },
                    { error ->
                        Log.e("Registration", "Error: ${error.message}")
                        Toast.makeText(context, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        return hashMapOf(
                            "username" to username,
                            "useremail" to email,
                            "userpassword" to password
                        )
                    }
                }

                queue.add(request)
            }
        }
    }
}
