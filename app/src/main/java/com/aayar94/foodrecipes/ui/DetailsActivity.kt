package com.aayar94.foodrecipes.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.ActivityDetailsBinding
import com.aayar94.foodrecipes.ui.ingredients.IngredientsFragment
import com.aayar94.foodrecipes.ui.instructions.InstructionsFragment
import com.aayar94.foodrecipes.ui.adapter.PagerAdapter
import com.aayar94.foodrecipes.ui.overview.OverviewFragment
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
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
        setupTabIcons(iconsList)
    }

    private fun setupTabIcons(iconsList: ArrayList<Int>) {
        binding.tabLayout.getTabAt(0)?.setIcon(iconsList[0])
        binding.tabLayout.getTabAt(1)?.setIcon(iconsList[1])
        binding.tabLayout.getTabAt(2)?.setIcon(iconsList[2])
    }

    private fun statusBarColorSetter() {
        val window = window
        val color = SurfaceColors.SURFACE_2.getColor(applicationContext)
        window!!.statusBarColor = color
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}