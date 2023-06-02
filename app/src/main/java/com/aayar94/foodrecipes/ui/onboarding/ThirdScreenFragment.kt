package com.aayar94.foodrecipes.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentThirdScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ThirdScreenFragment : Fragment() {
    private var mBinding: FragmentThirdScreenBinding? = null
    private val binding get() = mBinding!!
    private lateinit var viewModel: OnBoardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[OnBoardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentThirdScreenBinding.inflate(layoutInflater)
        binding.imageViewOnBoardingThirdScreen.load(R.raw.cutting_board)
        binding.screenThreeFinish.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToRecipesFragment()
            findNavController().navigate(action)
            onBoardingFinishedOnce()
        }
        return binding.root
    }

    private fun onBoardingFinishedOnce() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.saveLandingFinished(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}