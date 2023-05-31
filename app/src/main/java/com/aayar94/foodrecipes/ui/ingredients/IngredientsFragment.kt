package com.aayar94.foodrecipes.ui.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.FragmentIngredientsBinding
import com.aayar94.foodrecipes.utils.Constants.Companion.RECIPE_KEY_RESULT

class IngredientsFragment : Fragment() {
    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        val args = arguments
        val mBundle: com.aayar94.foodrecipes.model.Result? = args?.getParcelable(RECIPE_KEY_RESULT)


        setupRecyclerView(binding)
        mBundle?.extendedIngredients?.let { mAdapter.setData(it) }

        return binding.root
    }

    private fun setupRecyclerView(binding: FragmentIngredientsBinding) {
        binding.ingredientsRecyclerView.adapter = mAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}