package com.aayar94.foodrecipes.data

import com.aayar94.foodrecipes.data.network.FoodRecipesAPI
import com.aayar94.foodrecipes.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesAPI: FoodRecipesAPI
){
    suspend fun getRecipes(queries : Map<String,String>): Response<FoodRecipe>{
        return foodRecipesAPI.getRecipes(queries)
    }
}