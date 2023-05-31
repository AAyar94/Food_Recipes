package com.aayar94.foodrecipes.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentOverviewBinding
import com.aayar94.foodrecipes.utils.Constants.Companion.RECIPE_KEY_RESULT
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {
    private var mBinding: FragmentOverviewBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        var args = arguments
        var myBundle: com.aayar94.foodrecipes.model.Result? = args?.getParcelable(RECIPE_KEY_RESULT)
        binding.mainImageView.load(myBundle?.image)
        binding.overviewTitleTextView.text = myBundle?.title
        binding.likeTextView.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()

        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.sumaryTextView.text = summary
        }

        if (myBundle?.vegetarian == true) {
            binding.vegatarianImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.vegetarianTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.vegan == true) {
            binding.veganImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.veganTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.glutenFree == true) {
            binding.glutenfreeImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.glutenfreeTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.dairyFree == true) {
            binding.dairyfreeImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.dairyfreeTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.veryHealthy == true) {
            binding.healtyImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.healtyTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.cheap == true) {
            binding.cheapImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.cheapTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}