package com.aayar94.foodrecipes.data.network

import com.aayar94.foodrecipes.models.FoodRecipe
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesAPI {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): retrofit2.Response<FoodRecipe>
}