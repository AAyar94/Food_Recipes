package com.aayar94.foodrecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aayar94.foodrecipes.model.FoodRecipe
import com.aayar94.foodrecipes.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}