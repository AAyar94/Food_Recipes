package com.aayar94.foodrecipes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aayar94.foodrecipes.models.FoodRecipe
import com.aayar94.foodrecipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe

) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}