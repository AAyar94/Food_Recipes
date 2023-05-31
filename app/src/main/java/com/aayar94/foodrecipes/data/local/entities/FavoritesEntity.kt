package com.aayar94.foodrecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aayar94.foodrecipes.model.Result
import com.aayar94.foodrecipes.utils.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)