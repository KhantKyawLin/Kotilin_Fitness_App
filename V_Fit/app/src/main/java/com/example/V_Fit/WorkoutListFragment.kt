package com.example.V_Fit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentWorkoutListBinding
import org.json.JSONArray

class WorkoutListFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false)

        val userEmail = arguments?.getString("userEmail")
        val userID = arguments?.getInt("userID")

        Log.d("Workout List***", "Workout List: $userEmail, $userID")

        getWorkouts(userID)

        return binding.root
    } //onCreateView

    private fun getWorkouts(userID: Int?) {
        if (userID == null) return

        val url = "http://10.0.2.2/V_Fit_API/getWorkouts.php"
        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val workoutArray = JSONArray(response)
                    val list = ArrayList<WorkoutModel>()

                    for (i in 0 until workoutArray.length()) {
                        val jsonObj = workoutArray.getJSONObject(i)
                        val model = WorkoutModel(
                            workoutID = jsonObj.getInt("workoutID"),
                            workoutType = jsonObj.getString("workoutType"),
                            workoutDate = jsonObj.getString("workoutDate"),
                            workoutDay = jsonObj.getInt("workoutDay"),
                            workoutMonth = jsonObj.getInt("workoutMonth"),
                            workoutYear = jsonObj.getInt("workoutYear"),
                            workoutTime = jsonObj.getString("workoutTime"),
                            indoorOutdoor = jsonObj.getString("indoor_outdoor"),
                            equipments = jsonObj.getString("equipments"),
                            distance = jsonObj.getDouble("distance"),
                            duration = jsonObj.getInt("duration"),
                            workoutWeight = jsonObj.getDouble("workoutWeight"),
                            remark = jsonObj.getString("remark"),
                            userID = jsonObj.getInt("userID")
                        )
                        list.add(model)
                    }

                    binding.workoutRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.workoutRecyclerView.adapter = workoutAdapter(requireContext(), list)
                } catch (e: Exception) {
                    Toast.makeText(context, "Error parsing workouts: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("Workout List***", "Parsing Error: $e")
                }
            },
            { error ->
                Toast.makeText(context, "Network error: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("Workout List***", "Workout List Error: $error")
            }
        ) {
            override fun getParams(): HashMap<String, String>? {
                return HashMap<String, String>().apply {
                    put("userID", userID.toString())
                }
            }
        }
        queue.add(request)
    }

} //WorkoutListFragment