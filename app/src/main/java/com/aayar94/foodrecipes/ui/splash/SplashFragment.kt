package com.aayar94.foodrecipes.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentSplashBinding
import com.aayar94.foodrecipes.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var mBinding: FragmentSplashBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: OnBoardingViewModel by viewModels()
    private var isLandedBefore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        lifecycleScope.launch {
            viewModel.readLandingFinished.collect() {
                isLandedBefore = it
            }
        }
        binding.splashLogoImageView.load(R.drawable.ic_launcher_foreground) {
            crossfade(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1000)
            if (isLandedBefore) {
                val action = SplashFragmentDirections.actionSplashFragmentToRecipesFragment()
                findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
                findNavController().navigate(action)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.clearFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}