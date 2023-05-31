package com.aayar94.foodrecipes.ui.onboarding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aayar94.foodrecipes.databinding.FragmentOnboardingBinding
import com.aayar94.foodrecipes.ui.fragment.onboarding.FirstScreenFragment
import com.aayar94.foodrecipes.ui.fragment.onboarding.adapter.OnboardingViewPagerAdapter


class OnBoardingFragment : Fragment() {
    private var mBinding: FragmentOnboardingBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        /*val windowInsetsController =
            activity?.window?.let { WindowCompat.getInsetsController(it, activity?.window!!.decorView) }

        windowInsetsController?.show(WindowInsetsCompat.Type.systemBars())*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentOnboardingBinding.inflate(layoutInflater)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )
        val adapter = OnboardingViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.onBoardingFragmentViewPager.adapter = adapter

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}