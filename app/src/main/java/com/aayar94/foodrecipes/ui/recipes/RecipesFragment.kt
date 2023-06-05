package com.aayar94.foodrecipes.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentRecipesBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.aayar94.foodrecipes.utils.NetworkResult
import com.aayar94.foodrecipes.utils.NetworkStateListener
import com.aayar94.foodrecipes.utils.observeOnce
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {
    private var mBinding: FragmentRecipesBinding? = null
    private val binding get() = mBinding!!
    private val args by navArgs<RecipesFragmentArgs>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }

    private lateinit var networkStateListener: NetworkStateListener

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
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        windowColorSetter()
        setupRecyclerView()
        setupMenu()

        recipesViewModel.readBackOnline.asLiveData().observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launchWhenStarted {
            networkStateListener = NetworkStateListener()
            networkStateListener.checkNetworkAvailable(requireContext()).collect { status ->
                Log.d("Network Status", status.toString())
                recipesViewModel.networkStatus = status
                recipesViewModel.showNetworkStatus()
                readDatabase()
            }
        }

        binding.recipesFAB.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                val action = RecipesFragmentDirections.actionRecipesFragmentToRecipesBottomSheet()
                findNavController().navigate(action)
            } else {
                recipesViewModel.showNetworkStatus()
            }

        }

        return binding.root
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.recipes_menu, menu)

                val search = menu.findItem(R.id.menu_Search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@RecipesFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> requireActivity().onBackPressed()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding.shimmerRecyclerView.adapter = adapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
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
        window!!.statusBarColor = color
        window.navigationBarColor = color
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

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { adapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
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
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
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

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}