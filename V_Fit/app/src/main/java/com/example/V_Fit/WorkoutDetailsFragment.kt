package com.example.V_Fit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.V_Fit.databinding.FragmentWorkoutDetailsBinding

class WorkoutDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)

        // Use getParcelable to retrieve the WorkoutModel
        val workoutModel = arguments?.getParcelable<WorkoutModel>("workoutmodel")
        Log.d("WorkoutDetails***", "Deserialized WorkoutModel: $workoutModel")

        if (workoutModel == null) {
            Toast.makeText(requireContext(), "Workout data not found", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
            return binding.root
        }

        binding.txtWorkoutType.text = workoutModel.workoutType

        val text = StringBuilder()
        text.append("${workoutModel.workoutDate} ${workoutModel.workoutTime}\n")
        text.append("${workoutModel.indoorOutdoor}\n${workoutModel.equipments}\n\n")
        text.append("${workoutModel.distance} km\n")
        text.append("${workoutModel.duration} minutes\n")
        text.append("${workoutModel.workoutWeight} kg\n")
        text.append(workoutModel.remark)

        binding.txtWorkoutDetails.text = text.toString()

        binding.btnBacktoList.setOnClickListener {
            val userID = workoutModel.userID
            Log.d("WorkoutDetails***", "Navigating back with userID: $userID")
            if (userID != null) {
                val action = WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToWorkoutListFragment(userID)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}