package com.aayar94.foodrecipes.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aayar94.foodrecipes.databinding.FragmentRecipesBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.aayar94.foodrecipes.utils.Constants.Companion.API_KEY
import com.aayar94.foodrecipes.utils.NetworkResult
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var mBinding: FragmentRecipesBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mainViewModel: MainViewModel
    private val adapter by lazy { RecipesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        requestAPIData()
        windowColorSetter()

        return binding.root
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
            applyQueries()
        )
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
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

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun setupRecyclerView() {
        binding.shimmerRecyclerView.adapter = adapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    private fun showShimmerEffect() {
        binding.shimmerRecyclerView.showShimmer()
    }


}