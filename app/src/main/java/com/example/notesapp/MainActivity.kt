package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

         navController = findNavController(R.id.fragmentContainerView)

        // Check if ActionBar is not null before setting up with NavController
        supportActionBar?.let { actionBar ->
            setupActionBarWithNavController(navController, actionBar)
        }

    }

    private fun setupActionBarWithNavController(
        navController: NavController,
        actionBar: ActionBar
    ) {

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}