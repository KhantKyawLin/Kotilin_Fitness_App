package com.example.V_Fit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentEntryBinding


class EntryFragment : Fragment() {

    private lateinit var binding: FragmentEntryBinding
    private val workoutTypes = arrayOf("Choose your workout type...", "Running", "Cycling", "Walking", "Swimming", "Lifting Weight", "Jumping rope")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryBinding.inflate(inflater, container, false)

        val username = arguments?.getString("username")
        val userID = arguments?.getInt("userID")

        Log.d("Entry***", "Entry: $username, $userID")

        if (userID == null) {
            Toast.makeText(context, "User ID is missing", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
            return binding.root
        }

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.workout_spinner_item, workoutTypes)
        arrayAdapter.setDropDownViewResource(R.layout.workout_spinner_dropdown_item)
        binding.spinnerWorkoutType.adapter = arrayAdapter

        var selectedIndex = 0
        var selectedWorkoutType = ""

        binding.spinnerWorkoutType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedWorkoutType = workoutTypes[position]
                selectedIndex = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var pickedDate = ""
        var pickedDay = 0
        var pickedMonth = 0
        var pickedYear = 0

        binding.txtWorkoutDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                pickedDay = d
                pickedMonth = m + 1
                pickedYear = y
                pickedDate = "$d/${m + 1}/$y"
                binding.txtWorkoutDate.text = pickedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        var pickedTime = ""

        binding.txtWorkoutTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(requireContext(), { _, hr, min ->
                val ampm = if (hr >= 12) " PM" else " AM"
                val hour = if (hr >= 12) hr - 12 else hr
                pickedTime = String.format("%02d:%02d", hour, min) + ampm
                binding.txtWorkoutTime.text = pickedTime
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }

        binding.btnAddWorkout.setOnClickListener {
            var selectedItem = ""
            val selectedID = binding.rdoGroup.checkedRadioButtonId
            if (selectedID != -1) {
                val rdoItem = view?.findViewById<RadioButton>(selectedID)
                selectedItem = rdoItem?.text.toString() ?: ""
            }

            val checkedEquipments = StringBuilder()
            val bar = if (binding.checkBar.isChecked) "Barbell" else ""
            val dump = if (binding.checkDumb.isChecked) "Dumbbell" else ""
            val rope = if (binding.checkRope.isChecked) "Rope" else ""
            checkedEquipments.append("$bar, $dump, $rope")

            if (selectedIndex == 0) {
                Toast.makeText(context, "Please choose a workout type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pickedDate.isEmpty() || pickedTime.isEmpty()) {
                Toast.makeText(context, "Please select a date and time", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val distanceText = binding.editDistance.text.toString()
            val durationText = binding.editDuration.text.toString()
            val weightText = binding.editWeight.text.toString()
            val remark = binding.editRemark.text.toString()

            if (distanceText.isEmpty() || durationText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val distance = try {
                distanceText.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Invalid distance format", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val duration = try {
                durationText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Invalid duration format", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val workoutWeight = try {
                weightText.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Invalid weight format", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val model = WorkoutModel(
                workoutID = 0,
                workoutType = selectedWorkoutType,
                workoutDate = pickedDate,
                workoutDay = pickedDay,
                workoutMonth = pickedMonth,
                workoutYear = pickedYear,
                workoutTime = pickedTime,
                indoorOutdoor = selectedItem,
                equipments = checkedEquipments.toString(),
                distance = distance,
                duration = duration,
                workoutWeight = workoutWeight,
                remark = remark,
                userID = userID
            )

            saveWorkout(model)
        }

        binding.btnClearWorkout.setOnClickListener {
            binding.editDistance.setText("")
            binding.txtWorkoutDate.setText("Please Set Workout Date...")
            binding.txtWorkoutTime.setText("Please Set Workout Time...")
            binding.rdoGroup.clearCheck()
            binding.checkBar.isChecked = false
            binding.checkDumb.isChecked = false
            binding.checkRope.isChecked = false
        }

        return binding.root
    }

    private fun saveWorkout(model: WorkoutModel) {
        val url = "http://10.0.2.2/V_Fit_API/addWorkout.php"
        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Method.POST, url,
            { response ->
                if (response.contains("successfully")) {
                    Toast.makeText(context, "Workout added successfully", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_entryFragment_to_workoutListFragment, bundleOf("userID" to model.userID))
                } else {
                    Toast.makeText(context, "Failed to add workout: $response", Toast.LENGTH_LONG).show()
                }
                Log.d("Registration", "Registration Response: $response")
            },
            { error ->
                Toast.makeText(context, "Network error: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("Registration", "Registration Error: $error")
            }
        ) {
            override fun getParams(): HashMap<String, String>? {
                val map = HashMap<String, String>().apply {
                    put("workoutType", model.workoutType ?: "")
                    put("workoutDate", model.workoutDate ?: "")
                    put("workoutDay", model.workoutDay.toString())
                    put("workoutMonth", model.workoutMonth.toString())
                    put("workoutYear", model.workoutYear.toString())
                    put("workoutTime", model.workoutTime ?: "")
                    put("indoorOutdoor", model.indoorOutdoor ?: "")
                    put("equipments", model.equipments ?: "")
                    put("distance", model.distance.toString())
                    put("duration", model.duration.toString())
                    put("workoutWeight", model.workoutWeight.toString())
                    put("remark", model.remark ?: "")
                    put("userID", model.userID.toString())
                }
                Log.d("EntryFragment", "Parameters: $map")
                return map
            }
        }
        queue.add(request)
    }

    private fun showAlertDialog(title: String, msg: String) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }
}