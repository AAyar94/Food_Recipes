package com.aayar94.foodrecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aayar94.foodrecipes.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    private var mBinding: FragmentRecipesBinding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        binding.shimmerRecyclerView.showShimmer()
        return binding.root
    }

}