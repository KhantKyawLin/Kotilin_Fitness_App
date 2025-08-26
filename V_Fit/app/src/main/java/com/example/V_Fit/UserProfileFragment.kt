package com.example.V_Fit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentUserProfileBinding
import org.json.JSONObject

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        val userEmail = arguments?.getString("userEmail")
        val userID = arguments?.getInt("userID")

        Log.d("Profile***", "Profile: $userEmail, $userID")

        getUserProfile(userID)

        return binding.root

    } //onCreateView

    private fun getUserProfile(userID:Int?) {
        val url = "http://10.0.2.2/V_Fit_API/userProfile.php"  //localhost

        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Method.POST,url,{
                //success listener
                    response ->

                Log.d("Profile***", "Profile Response: $response")

                val userData = JSONObject(response) //string to JSONObject

                Log.d("Profile***","ID: " + userData.getInt("userID"))

                binding.txtFullName.text = userData.getString("username")
                binding.txtUserEmail.text = userData.getString("useremail")

            }, {
                //error listener
                    error ->
                Log.d("Profile***", "Profile Error: $error")
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
    } //getUserProfile

} //UserProfileFragment