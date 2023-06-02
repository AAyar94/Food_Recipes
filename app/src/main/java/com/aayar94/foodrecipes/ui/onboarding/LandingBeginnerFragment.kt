package com.aayar94.foodrecipes.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentLandingBeginnerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingBeginnerFragment : Fragment() {
    private var mBinding: FragmentLandingBeginnerBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLandingBeginnerBinding.inflate(layoutInflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingFragment_ViewPager)
        binding.letsCookButton.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }
}