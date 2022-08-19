package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Recipe


interface InteractionListener {
    fun shareOnClick(recipe: Recipe)
    fun editOnClick(recipe: Recipe)
    fun deleteOnClick(recipe: Recipe)
    fun filterOnClick(recipe: Recipe)
    fun frameOnShortClick(recipe: Recipe)
    fun frameOnLongClick(recipe: Recipe)
}