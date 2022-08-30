package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Recipe


interface RecipeInteractionListener {
    fun favoriteOnClick(recipe: Recipe)
    fun editOnClick(recipe: Recipe)
    fun deleteOnClick(recipe: Recipe)
    fun filterOnClick()
    fun searchBarOnClick(string: String)
    fun frameOnShortClick(recipe: Recipe)
    fun frameOnLongClick(recipe: Recipe)
    fun saveOnClick(recipe: Recipe)
}