package com.aayar94.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aayar94.foodrecipes.data.database.entities.FavoritesEntity
import com.aayar94.foodrecipes.databinding.RowLayoutFavoritesBinding
import com.aayar94.foodrecipes.util.RecipesDiffUtil

class FavoriteRecipesAdapter : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {
    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: RowLayoutFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutFavoritesBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffutil = RecipesDiffUtil(
            favoriteRecipes, newFavoriteRecipes
        )
        val favoriteRecipesDiffutilResult = DiffUtil.calculateDiff(favoriteRecipesDiffutil)
        favoriteRecipes = newFavoriteRecipes
        favoriteRecipesDiffutilResult.dispatchUpdatesTo(this)
    }
}