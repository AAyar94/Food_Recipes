package com.aayar94.foodrecipes.ui.food_joke

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aayar94.foodrecipes.BuildConfig
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentFoodJokeBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.aayar94.foodrecipes.utils.NetworkResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private var mBinding: FragmentFoodJokeBinding? = null
    private val binding get() = mBinding!!
    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodJokeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                val drawable =
                    requireContext().resources.getDrawable(R.drawable.food_joke_bg_dark, null)
                binding.root.background = drawable
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                val drawable =
                    requireContext().resources.getDrawable(R.drawable.food_joke_bg_light, null)
                binding.root.background = drawable
            }

            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                val drawable =
                    requireContext().resources.getDrawable(R.drawable.food_joke_bg_light, null)
                binding.root.background = drawable
            }
        }

        mainViewModel.getFoodJoke(BuildConfig.FOOD_RECIPES_API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showContent()
                    hideError()
                    hideLoading()
                    binding.foodJokeTextView.text = response.data?.text
                }

                is NetworkResult.Loading -> {
                    hideContent()
                    hideError()
                    showLoading()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    hideContent()
                    showError()
                    loadFromCache()
                    showSnackbar(getString(R.string.loaded_from_cache))
                }
            }

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    private fun showSnackbar(message: String?) {
        if (message != null) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun loadFromCache() {
        mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty() && database != null) {
                binding.foodJokeTextView.text = database[0].foodJoke.text
            }

        }
    }

    fun showLoading() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.progressCircular.visibility = View.GONE
    }

    fun showError() {
        binding.errorTextView.visibility = View.VISIBLE
        binding.foodJokeErrorImage.visibility = View.VISIBLE
    }

    fun hideError() {
        binding.errorTextView.visibility = View.GONE
        binding.foodJokeErrorImage.visibility = View.GONE
    }

    fun showContent() {
        binding.cardView.visibility = View.VISIBLE
    }

    fun hideContent() {
        binding.cardView.visibility = View.GONE
    }
}