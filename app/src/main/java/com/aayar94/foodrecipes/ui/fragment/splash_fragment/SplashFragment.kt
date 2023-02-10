package com.aayar94.foodrecipes.ui.fragment.splash_fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aayar94.foodrecipes.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splashFragment_to_recipesFragment)
            }, 3000
        )

        return view
    }


}