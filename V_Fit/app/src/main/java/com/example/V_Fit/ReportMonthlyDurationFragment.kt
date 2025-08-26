package com.example.V_Fit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentReportMonthlyDurationBinding
import org.json.JSONArray
import org.json.JSONObject

class ReportMonthlyDurationFragment : Fragment() {

    private lateinit var binding: FragmentReportMonthlyDurationBinding

    val monthList = arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReportMonthlyDurationBinding.inflate(inflater, container, false)

        val userID = arguments?.getInt("userID")
        Log.d("Report***", "UserID: $userID")

        getMonthlyDuration(userID!!)

        return binding.root
    }

    private fun getMonthlyDuration(userID: Int){
        val url = "http://10.0.2.2/V_Fit_API/getMonthlyReport.php"  //localhost

        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Method.POST,url,{
                //success listener
                    response ->

                Log.d("Workout Report***", "Workout Report Response: $response")

                val workoutArray = JSONArray(response) //string to JSONArray

                val length = workoutArray.length()

                val list = ArrayList<WorkoutModel>()

                for (i in 0..< length){
                    val jsonObj: JSONObject = workoutArray.get(i) as JSONObject

                    val totalDuration = jsonObj.getInt("total_duration")
                    val month = jsonObj.getInt("workoutMonth")

                    monthList[month-1] = totalDuration
                } //for

                val barSet = listOf(
                    "JAN" to monthList[0].toFloat(),
                    "FEB" to monthList[1].toFloat(),
                    "MAR" to monthList[2].toFloat(),
                    "APR" to monthList[3].toFloat(),
                    "MAY" to monthList[4].toFloat(),
                    "JUNE" to monthList[5].toFloat(),
                    "JUL" to monthList[6].toFloat(),
                    "AUG" to monthList[7].toFloat(),
                    "SEP" to monthList[8].toFloat(),
                    "OCT" to monthList[9].toFloat(),
                    "NOV" to monthList[10].toFloat(),
                    "DEC" to monthList[11].toFloat()
                )

                binding.barChartReport.animate(barSet)
                binding.barChartReport.invalidate()

            }, {
                //error listener
                    error ->
                Log.d("Workout List***", "Workout List Error: $error")
            }
        ){
            override fun getParams(): HashMap<String, String>? {

                val map = HashMap<String, String>()
                map.apply {
                    put("userID", userID.toString())
                }

                return map
            } //params
        } //string request

        queue.add(request)
    } //getMonthlyDuration
} //onCreateView