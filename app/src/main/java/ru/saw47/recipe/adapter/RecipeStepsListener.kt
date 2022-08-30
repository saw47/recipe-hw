package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Recipe


interface RecipeStepsListener {
    fun editStepOnClick(recipe: Recipe)
    fun deleteStepOnClick(recipe: Recipe)
}