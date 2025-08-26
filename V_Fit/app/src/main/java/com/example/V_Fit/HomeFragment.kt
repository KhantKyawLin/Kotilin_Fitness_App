package com.example.V_Fit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.V_Fit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val userEmail = arguments?.getString("userEmail")
        val userID = arguments?.getInt("userID")

        Log.d("Home***", "Home: $userEmail, $userID")

        binding.btnShowReport.setOnClickListener {
            val userID = arguments?.getInt("userID") ?: return@setOnClickListener
            val action = HomeFragmentDirections.actionHomeFragmentToReportMonthlyDurationFragment(userID)
            findNavController().navigate(action)
        }

        binding.btnGetLocation.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToLocationFragment()
            findNavController().navigate(action)
        }

        return binding.root

    } //onCreateView


}