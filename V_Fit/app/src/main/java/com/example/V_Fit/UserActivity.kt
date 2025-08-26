package com.example.V_Fit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.ActivityUserBinding
import org.json.JSONObject

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        bundle = intent.extras
        val userEmail = bundle?.getString("userEmail")
        val userID = bundle?.getInt("userID")

        if (userID == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Log.i("user***", "Useremail: $userEmail...UserID: $userID")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.userFragmentView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)
        navController.navigate(R.id.homeFragment, bundle)

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_user_home -> {
                    navController.navigate(R.id.homeFragment, bundle)
                    true
                }
                R.id.item_user_entry -> {
                    navController.navigate(R.id.entryFragment, bundle)
                    true
                }
                R.id.item_workout_list -> {
                    navController.navigate(R.id.workoutListFragment, bundle)
                    true
                }
                R.id.item_user_profile -> {
                    navController.navigate(R.id.userProfileFragment, bundle)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() //UserActivity
            }//logout
        }//when
        return super.onOptionsItemSelected(item)
    }//ItemSelected

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        return true
    }

    // Workout Details
    fun showWorkoutDetails(workoutID: Int) {
        Log.d("Workout List***", "Fetching details for workoutID: $workoutID")
        val url = "http://10.0.2.2/V_Fit_API/getWorkoutDetails.php"
        val queue = Volley.newRequestQueue(this)

        val request = object : StringRequest(Method.POST, url, { response ->
            Log.d("Workout List***", "Workout List Response: $response")

            try {
                val jsonObj = JSONObject(response)

                if (jsonObj.has("error")) {
                    Toast.makeText(this, jsonObj.getString("error"), Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.workoutListFragment, bundle ?: Bundle())
                } else {
                    val workoutID = jsonObj.optString("workoutID")?.toIntOrNull() ?: 0
                    val workoutType = jsonObj.optString("workoutType", "")
                    val workoutDate = jsonObj.optString("workoutDate", "")
                    val workoutDay = jsonObj.optString("workoutDay")?.toIntOrNull() ?: 0
                    val workoutMonth = jsonObj.optString("workoutMonth")?.toIntOrNull() ?: 0
                    val workoutYear = jsonObj.optString("workoutYear")?.toIntOrNull() ?: 0
                    val workoutTime = jsonObj.optString("workoutTime", "")
                    val indoorOutdoor = jsonObj.optString("indoor_outdoor", "")
                    val equipments = jsonObj.optString("equipments", "")
                    val distance = jsonObj.optString("distance")?.toDoubleOrNull() ?: 0.0
                    val duration = jsonObj.optString("duration")?.toIntOrNull() ?: 0
                    val workoutWeight = jsonObj.optString("workoutWeight")?.toDoubleOrNull() ?: 0.0
                    val remark = jsonObj.optString("remark", "")
                    val userID = jsonObj.optString("userID")?.toIntOrNull() ?: 0

                    val model = WorkoutModel(
                        workoutID = workoutID,
                        workoutType = workoutType,
                        workoutDate = workoutDate,
                        workoutDay = workoutDay,
                        workoutMonth = workoutMonth,
                        workoutYear = workoutYear,
                        workoutTime = workoutTime,
                        indoorOutdoor = indoorOutdoor,
                        equipments = equipments,
                        distance = distance,
                        duration = duration,
                        workoutWeight = workoutWeight,
                        remark = remark,
                        userID = userID
                    )
                    Log.d("Workout List***", "Created WorkoutModel before serialization: $model")

                    // Create a Bundle and include both workoutmodel and userID
                    val workoutBundle = Bundle().apply {
                        putParcelable("workoutmodel", model)
                        putInt("userID", userID)
                    }
                    Log.d("Workout List***", "Navigating to WorkoutDetailsFragment with userID: $userID")
                    navController.navigate(R.id.workoutDetailsFragment, workoutBundle)
                }
            } catch (e: Exception) {
                Log.e("Workout List***", "Parsing Error: $e", e)
                Toast.makeText(this, "Error parsing workout details: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }, { error ->
            Log.e("Workout List***", "Workout List Error: $error")
            Toast.makeText(this, "Network error: ${error.message}", Toast.LENGTH_LONG).show()
        }) {
            override fun getParams(): HashMap<String, String> {
                val map = HashMap<String, String>()
                map["workoutID"] = workoutID.toString()
                return map
            }
        }

        queue.add(request)
    }

    private fun deleteWorkoutAction(workoutID: Int) {
        val url = "http://10.0.2.2/V_Fit_API/deleteWorkout.php"
        val queue = Volley.newRequestQueue(this)

        val request = object : StringRequest(Method.POST, url, { response ->
            Log.d("Delete***", "Delete Response: $response")
            Toast.makeText(this, "Successfully Deleted!", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.workoutListFragment, bundle)
        }, { error ->
            Log.d("Delete***", "Delete Error: $error")
        }) {
            override fun getParams(): HashMap<String, String> {
                val map = HashMap<String, String>()
                map["workoutID"] = workoutID.toString()
                return map
            }
        }

        queue.add(request)
    }

    fun deleteWorkout(workoutID: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setPositiveButton("YES") { dialog, _ ->
                deleteWorkoutAction(workoutID)
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
