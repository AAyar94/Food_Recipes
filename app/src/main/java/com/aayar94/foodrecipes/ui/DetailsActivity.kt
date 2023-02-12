package com.aayar94.foodrecipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.adapter.PagerAdapter
import com.aayar94.foodrecipes.data.database.entities.FavoritesEntity
import com.aayar94.foodrecipes.databinding.ActivityDetailsBinding
import com.aayar94.foodrecipes.ui.fragment.Ingredients.IngredientsFragment
import com.aayar94.foodrecipes.ui.fragment.Instructions.InstructionsFragment
import com.aayar94.foodrecipes.ui.fragment.overview.OverviewFragment
import com.aayar94.foodrecipes.ui.viewmodel.MainViewModel
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved = false
    private var savedRecipeId = 0


    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.text_color))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val window = window
        val color = SurfaceColors.SURFACE_2.getColor(applicationContext)
        window!!.statusBarColor = color // Set color of system statusBar same as ActionBar
        window.navigationBarColor =
            android.R.color.transparent // Set color of system navigationBar same as BottomNavigationView

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instruction")

        val iconsList = ArrayList<Int>()
        iconsList.add(R.drawable.ic_view)
        iconsList.add(R.drawable.ic_ingredients)
        iconsList.add(R.drawable.ic_information)


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle, fragments, titles, supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.setTabIconTintResource(R.color.primaryColorApp)
        setupTabIcons()

        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !recipeSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout, message, Snackbar.LENGTH_LONG
        ).setAction("Okey") {}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun setupTabIcons() {
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_view)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_ingredients)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_information)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                    } else {
                        changeMenuItemColor(menuItem, R.color.text_color)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }
}