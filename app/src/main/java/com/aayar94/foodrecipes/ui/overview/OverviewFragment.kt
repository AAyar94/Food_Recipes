package com.aayar94.foodrecipes.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.binding_adapters.RowRecipesBinding
import com.aayar94.foodrecipes.databinding.FragmentOverviewBinding
import com.aayar94.foodrecipes.model.Result
import com.aayar94.foodrecipes.utils.Constants.Companion.RECIPE_KEY_RESULT


class OverviewFragment : Fragment() {
    private var mBinding: FragmentOverviewBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_KEY_RESULT) as Result

        binding.mainImageView.load(myBundle.image)
        binding.overviewTitleTextView.text = myBundle.title
        binding.likeTextView.text = myBundle.aggregateLikes.toString()
        binding.timeTextView.text = myBundle.readyInMinutes.toString()


        RowRecipesBinding.parseHtml(binding.sumaryTextView, myBundle.summary)

        updateColors(myBundle.vegetarian, binding.vegetarianTextView, binding.vegatarianImageView)
        updateColors(myBundle.vegan, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)
        updateColors(myBundle.dairyFree, binding.dairyfreeTextView, binding.dairyfreeImageView)
        updateColors(myBundle.glutenFree, binding.glutenfreeTextView, binding.glutenfreeImageView)
        updateColors(myBundle.veryHealthy, binding.healtyTextView, binding.healtyImageView)

        return binding.root
    }

    private fun updateColors(stateOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}