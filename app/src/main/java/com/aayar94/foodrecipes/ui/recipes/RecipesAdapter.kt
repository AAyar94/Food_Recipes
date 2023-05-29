package com.aayar94.foodrecipes.ui.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aayar94.foodrecipes.databinding.RowLayoutRecipesBinding
import com.aayar94.foodrecipes.model.FoodRecipe
import com.aayar94.foodrecipes.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    private var recipes = emptyList<com.aayar94.foodrecipes.model.Result>()

    class RecipesViewHolder(private val binding: RowLayoutRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: com.aayar94.foodrecipes.model.Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutRecipesBinding.inflate(
                    layoutInflater, parent, false
                )
                return RecipesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)

    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        //notifyDataSetChanged()
        diffUtilResult.dispatchUpdatesTo(this)
    }
}