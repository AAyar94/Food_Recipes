package com.aayar94.foodrecipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.adapter.PagerAdapter
import com.aayar94.foodrecipes.databinding.ActivityDetailsBinding
import com.aayar94.foodrecipes.ui.fragment.Ingredients.IngredientsFragment
import com.aayar94.foodrecipes.ui.fragment.Instructions.InstructionsFragment
import com.aayar94.foodrecipes.ui.fragment.overview.OverviewFragment
import com.google.android.material.elevation.SurfaceColors

class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()


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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupTabIcons() {
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_view)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_ingredients)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_information)


    }

}