package com.aayar94.foodrecipes.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aayar94.foodrecipes.databinding.FragmentRecipesBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.aayar94.foodrecipes.utils.NetworkResult
import com.aayar94.foodrecipes.utils.observeOnce
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var mBinding: FragmentRecipesBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRecipesBinding.inflate(layoutInflater, container, false)

        windowColorSetter()
        setupRecyclerView()
        readDatabase()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.shimmerRecyclerView.adapter = adapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestAPIData()
                }
            }
        }
    }

    private fun windowColorSetter() {
        val window = activity?.window
        val color = SurfaceColors.SURFACE_2.getColor(requireContext())
        window!!.statusBarColor = color // Set color of system statusBar same as ActionBar
        window.navigationBarColor =
            color // Set color of system navigationBar same as BottomNavigationView
    }

    private fun requestAPIData() {
        mainViewModel.getRecipes(
            recipesViewModel.applyQueries()
        )
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(), Toast.LENGTH_LONG
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    private fun showShimmerEffect() {
        binding.shimmerRecyclerView.showShimmer()
    }


}