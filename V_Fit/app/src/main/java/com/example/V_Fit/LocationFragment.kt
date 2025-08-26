package com.example.V_Fit

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.V_Fit.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    private lateinit var fusedLocationProviderClient:FusedLocationProviderClient

    private val REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater, container, false)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.btnGetCurrentLocation.setOnClickListener() {
            getLastLocation()
        }

        return binding.root
    }

    private fun getLastLocation() {

        Log.d("Location***","Location: GetLastLocation ")

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    try {

                        Log.d("Location***","Location: "+it.latitude+"..."+it.longitude)
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val addresses: List<Address>? = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        val address = addresses?.get(0)
                        binding.txtLatitude.text = "Lattitude: ${address!!.latitude}"
                        binding.txtLongitude.text = "Longitude: ${address.longitude}"
                        binding.txtAddress.text = "Address: ${address.getAddressLine(0)}"
                        binding.txtCity.text = "City: ${address.locality}"
                        binding.txtCountry.text = "Country: ${address.countryName}"
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            askPermission()
        }
    } //getLastLocation

    private fun askPermission() {
        ActivityCompat.requestPermissions(context as UserActivity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    } //askPermission

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults:
    IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Location***","Last Location")
                getLastLocation()
            } else {
                Toast.makeText(requireContext(), "Please provide the required permission", Toast.LENGTH_LONG).show()
            }
        }
    } //onRequestPermissionResult
}