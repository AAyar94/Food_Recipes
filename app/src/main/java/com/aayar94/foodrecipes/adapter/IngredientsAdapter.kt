package com.aayar94.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.IngredientsRowLayoutBinding
import com.aayar94.foodrecipes.model.ExtendedIngredient
import com.aayar94.foodrecipes.util.Constants.Companion.BASE_IMAGE_URL
import com.aayar94.foodrecipes.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private lateinit var binding: IngredientsRowLayoutBinding
    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(var binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        binding.ingredientName.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        binding.ingredientUnit.text = ingredientsList[position].unit.toString()
        binding.ingredientConsistency.text = ingredientsList[position].consistency.toString()
        binding.ingredientOriginal.text = ingredientsList[position].original.toString()
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: kotlin.collections.List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(
            ingredientsList, newIngredients
        )
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}