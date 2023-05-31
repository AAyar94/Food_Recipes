package com.aayar94.foodrecipes.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentSecondScreenBinding


class SecondScreenFragment : Fragment() {
    private var mBinding: FragmentSecondScreenBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentSecondScreenBinding.inflate(layoutInflater)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingFragment_ViewPager)
        binding.screenTwoNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        binding.imageViewOnBoardingSecondScreen.load(R.raw.two_drinks)


        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}