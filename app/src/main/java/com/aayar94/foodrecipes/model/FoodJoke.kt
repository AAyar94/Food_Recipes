package com.aayar94.foodrecipes.model

import com.google.gson.annotations.SerializedName


data class FoodJoke(
    @SerializedName("text")
    val text: String
)