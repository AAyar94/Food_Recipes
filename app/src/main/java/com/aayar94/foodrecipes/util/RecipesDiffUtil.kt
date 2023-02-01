package com.aayar94.foodrecipes.util

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil(
    private val oldList: List<com.aayar94.foodrecipes.model.Result>,
    private val newList: List<com.aayar94.foodrecipes.model.Result>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}