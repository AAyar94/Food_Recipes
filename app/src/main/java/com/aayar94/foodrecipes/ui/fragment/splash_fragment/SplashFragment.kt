package com.aayar94.foodrecipes.ui.fragment.splash_fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aayar94.foodrecipes.R


class SplashFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_splash, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            Handler().postDelayed({
                if (onBoardingFinished()) {
                    findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_recipesFragment)
                }
            }, timer())

            //goToRecipesFragment()
        }

    }

    private fun timer(): Long {
        return if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            100
        } else {
            3000
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }


    private fun goToRecipesFragment() {
        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
            }, 500
        )

    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

}