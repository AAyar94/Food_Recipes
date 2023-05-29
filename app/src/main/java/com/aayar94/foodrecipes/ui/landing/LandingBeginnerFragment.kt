package com.aayar94.foodrecipes.ui.landing

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentLandingBeginnerBinding

class LandingBeginnerFragment : Fragment() {
    private var mBinding: FragmentLandingBeginnerBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLandingBeginnerBinding.inflate(layoutInflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding.letsCookButton.setOnClickListener {
            val action =
                LandingBeginnerFragmentDirections.actionLandingBeginnerFragmentToRecipesFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}