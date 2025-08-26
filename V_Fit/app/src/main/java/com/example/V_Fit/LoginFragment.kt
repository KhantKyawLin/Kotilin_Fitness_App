package com.example.V_Fit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.V_Fit.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener(){
            loginAction()
        } //Login ClickListener

        binding.register.setOnClickListener(){
            val  action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        } //Register ClickListener

        // initialize animations

        var fade_in = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        var bottom_down = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_down)

        // setting the bottom down animation on top layout

        binding.topLinearLayout.animation = bottom_down

        // handler for other layouts

        var handler = Handler()
        var runnable = Runnable {
            // setting fade in animation on other layouts

            binding.imageView.animation = fade_in
            binding.textView.animation = fade_in
            binding.cardView.animation = fade_in
        }

        handler.postDelayed(runnable, 1000)

        return binding.root


    } //onCreateView

    private fun loginAction() {

        val userEmail = binding.txtUserEmail.text.toString()
        val userPassword = binding.txtPassword.text.toString()

        if (userEmail.isEmpty()){
            binding.txtUserEmail.error = "Email must be filled!"
        }
        else if (userPassword.isEmpty()){
            binding.txtPassword.error = "Password must be filled!"
        }
        else{
            //Login

            val url = "http://10.0.2.2/V_Fit_API/loginUser.php"  //localhost

            val queue = Volley.newRequestQueue(requireContext())

            val request = object : StringRequest(
                Method.POST,url,{
                    //success listener
                        response ->
                    Toast.makeText(context,"Response: $response",Toast.LENGTH_LONG).show()

                    Log.d("Login***", "Login Response: $response")

                    if (response == "0"){
                        showAlertDialog("Login Failure","Invalid User Email or Password! Try again!")
                    }
                    else{
                        //Login Success

                        Toast.makeText(requireContext(),"Login Success",Toast.LENGTH_LONG).show()

                        val intent = Intent(activity, UserActivity::class.java)
                        intent.putExtra("userEmail", userEmail) //key,value
                        intent.putExtra("userID", response.toInt())
                        startActivity(intent)
                        activity?.finish() //MainActivity
                    }

                }, {
                    //error listener
                        error ->
                    Log.d("Login***", "Login Error: $error")
                }
            ){
                override fun getParams(): HashMap<String, String>? {

                    val map = HashMap<String, String>()
                    map.apply {
                        put("useremail", userEmail)
                        put("userpassword", userPassword)
                    }

                    return map
                } //params
            } //string request

            queue.add(request)

        } //else

    } //loginAction

    private fun showAlertDialog(title:String, msg:String){
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK"){
                _,_ ->
            }
            .show()
    } //showAlertDialog


} //LoginFragment