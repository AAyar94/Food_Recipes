package com.aayar94.foodrecipes.data

import com.aayar94.foodrecipes.data.database.RecipesDao
import com.aayar94.foodrecipes.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {


    fun readDatabase(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

}