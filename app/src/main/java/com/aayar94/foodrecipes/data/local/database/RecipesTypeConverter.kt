package com.aayar94.foodrecipes.data.local.database

import androidx.room.TypeConverter
import com.aayar94.foodrecipes.model.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: com.aayar94.foodrecipes.model.Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): com.aayar94.foodrecipes.model.Result {
        val listType = object : TypeToken<com.aayar94.foodrecipes.model.Result>() {}.type
        return gson.fromJson(data, listType)
    }

}