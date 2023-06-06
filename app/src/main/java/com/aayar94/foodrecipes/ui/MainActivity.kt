package com.aayar94.foodrecipes.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.ActivityMainBinding
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment, R.id.favoritesFragment, R.id.foodJokeFragment
            )
        )

        binding.bottomNavBar.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        window.statusBarColor = MaterialColors.getColor(
            binding.root,
            com.google.android.material.R.attr.colorPrimaryContainer
        )
        window.navigationBarColor=MaterialColors.getColor(
            binding.root,
            com.google.android.material.R.attr.colorPrimary)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment -> binding.bottomNavBar.visibility = View.GONE
                R.id.splashFragment -> binding.bottomNavBar.visibility=View.GONE
                else -> binding.bottomNavBar.visibility = View.VISIBLE
            }
            when (destination.id) {
                R.id.splashFragment -> actionBar?.hide()
                R.id.onBoardingFragment -> actionBar?.hide()
                else -> actionBar?.show()
            }
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}