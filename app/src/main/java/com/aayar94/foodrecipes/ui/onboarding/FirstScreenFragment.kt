package com.aayar94.foodrecipes.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {
    private var mBinding: FragmentFirstScreenBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentFirstScreenBinding.inflate(layoutInflater)

        binding.imageViewOnBoardingFirstScreen.load(R.raw.cooking)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingFragment_ViewPager)
        binding.screenOneNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}