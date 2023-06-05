package com.aayar94.foodrecipes.ui.favorites

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.data.local.entities.FavoritesEntity
import com.aayar94.foodrecipes.databinding.RowLayoutFavoritesBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.aayar94.foodrecipes.utils.RecipesDiffUtil
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel,
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),
    ActionMode.Callback {
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View
    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var multiSelection = false
    private var myViewHolder = arrayListOf<MyViewHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

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
        myViewHolder.add(holder)
        rootView = holder.itemView.rootView
        myViewHolder.add(holder)
        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        saveItemsStateOnScroll(currentRecipe, holder)

        /**     Single Click Listener       * */
        holder.itemView.findViewById<ConstraintLayout>(R.id.favoriteRecipesRowLayout)
            .setOnClickListener {
                if (multiSelection) {
                    applySelection(holder, currentRecipe = currentRecipe)
                } else {
                    val action =
                        FavoritesFragmentDirections.actionFavoritesFragmentToDetailsActivity(
                            currentRecipe.result
                        )
                    holder.itemView.findNavController().navigate(action)
                }
            }

        /**     Long Click Listener     * */
        holder.itemView.findViewById<ConstraintLayout>(R.id.favoriteRecipesRowLayout)
            .setOnLongClickListener {
                if (!multiSelection) {
                    multiSelection = true
                    requireActivity.startActionMode(this)
                    applySelection(holder, currentRecipe)
                    true
                } else {
                    applySelection(holder, currentRecipe)
                    true
                }
            }
    }

    fun saveItemsStateOnScroll(currentRecipe: FavoritesEntity, holder: MyViewHolder) {
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(
                holder, MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorPrimaryContainer
                ), MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorOnPrimaryContainer
                )
            )
        } else {
            changeRecipeStyle(
                holder, MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorSurface
                ), MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorPrimary
                )
            )
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(
                holder, MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorSurface
                ), MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorPrimary
                )
            )
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(
                holder, MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorPrimaryContainer
                ), MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorOnPrimaryContainer
                )
            )
            applyActionModeTitle()
        }
    }


    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }

            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }

            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }

        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.findViewById<ConstraintLayout>(R.id.favoriteRecipesBackground)
            .setBackgroundColor(
                backgroundColor
            )
        holder.itemView.findViewById<MaterialCardView>(R.id.favorite_row_cardView).strokeColor =
            strokeColor
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffutil = RecipesDiffUtil(
            favoriteRecipes, newFavoriteRecipes
        )
        val favoriteRecipesDiffutilResult = DiffUtil.calculateDiff(favoriteRecipesDiffutil)
        favoriteRecipes = newFavoriteRecipes
        favoriteRecipesDiffutilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_mode, menu)
        mActionMode = actionMode!!
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteFavoriteRecipeMenu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe deleted.")
            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolder.forEach { holder ->
            changeRecipeStyle(
                holder, MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorSurface
                ), MaterialColors.getColor(
                    holder.itemView,
                    com.google.android.material.R.attr.colorPrimary
                )
            )
        }
        multiSelection = false
        selectedRecipes.clear()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView, message, Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}.show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}