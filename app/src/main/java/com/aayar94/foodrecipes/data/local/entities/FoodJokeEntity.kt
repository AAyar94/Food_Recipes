package com.aayar94.foodrecipes.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aayar94.foodrecipes.model.FoodJoke
import com.aayar94.foodrecipes.utils.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @Embedded
    val foodJoke: FoodJoke
)