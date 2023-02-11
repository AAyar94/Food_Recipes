package com.aayar94.foodrecipes.ui.fragment.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentThirdScreenBinding


class ThirdScreenFragment : Fragment() {
    private var mBinding: FragmentThirdScreenBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentThirdScreenBinding.inflate(layoutInflater)
        binding.imageViewOnBoardingThirdScreen.load(R.raw.cutting_board)
        binding.screenThreeFinish.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_recipesFragment)
            //onBoardingFinishedOnce()
        }




        return binding.root
    }

    private fun onBoardingFinishedOnce() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}