package com.aayar94.foodrecipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.data.local.entities.FavoritesEntity
import com.aayar94.foodrecipes.databinding.ActivityDetailsBinding
import com.aayar94.foodrecipes.ui.adapter.PagerAdapter
import com.aayar94.foodrecipes.ui.ingredients.IngredientsFragment
import com.aayar94.foodrecipes.ui.instructions.InstructionsFragment
import com.aayar94.foodrecipes.ui.overview.OverviewFragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(
            ContextCompat.getColor(this, R.color.black)
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        statusBarColorSetter()

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.overview))
        titles.add(getString(R.string.ingredients))
        titles.add(getString(R.string.instruction))

        val iconsList = ArrayList<Int>()
        iconsList.add(R.drawable.ic_overview)
        iconsList.add(R.drawable.ic_ingredients)
        iconsList.add(R.drawable.ic_instructions)


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle, fragments, titles, supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.setTabIconTintResource(R.color.black)
        setupTabIcons(iconsList)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
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
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.red)
        showSnackBar(getString(R.string.recipe_saved))
        recipeSaved = true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            favoritesEntity.find { savedRecipe ->
                savedRecipe.result.recipeId == args.result.recipeId
            }?.let { savedRecipe ->
                changeMenuItemColor(menuItem, R.color.red)
                savedRecipeId = savedRecipe.id
                recipeSaved = true
            } ?: changeMenuItemColor(menuItem, R.color.black)
        }
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.black)
        showSnackBar(getString(R.string.removed_from_favorites))
        recipeSaved = false
    }

    @SuppressLint("ResourceAsColor")
    private fun statusBarColorSetter() {
        val window = window
        window!!.statusBarColor = MaterialColors.getColor(
            binding.root,
            com.google.android.material.R.attr.colorSecondaryContainer
        )
        window.navigationBarColor = MaterialColors.getColor(
            binding.root,
            com.google.android.material.R.attr.colorSurface
        )
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout, message, Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.okay)) {}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    @SuppressLint("ResourceType")
    private fun setupTabIcons(iconsList: ArrayList<Int>) {
        with(binding.tabLayout) {
            for (i in 0 until iconsList.size) {
                getTabAt(i)?.setIcon(iconsList[i])
            }
        }
    }
}