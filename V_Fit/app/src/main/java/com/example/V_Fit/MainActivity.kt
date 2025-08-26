package com.example.V_Fit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.V_Fit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentView) as NavHostFragment
        Log.d("MainActivity", "NavHostFragment found: $navHostFragment")

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentView) as NavHostFragment

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    } //onCreate

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        return true
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        Log.d("MenuDebug", "onCreateOptionsMenu called")
//        menuInflater.inflate(R.menu.user_main_menu, menu)
//        invalidateOptionsMenu()
//
//        for (i in 0 until (menu?.size() ?: 0)) {
//            Log.d("MenuDebug", "Menu item: ${menu?.getItem(i)?.title}")
//        }
//        return true
//    }


} //MainActivity