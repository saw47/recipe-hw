package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe


interface StepsInteractionListener {
    fun editStepOnClick(step: Step)
    fun deleteStepOnClick(step: Step)
    fun saveStepOnClick(step: Step)
    fun addNewStepOnClick(recipe: Recipe)
    fun cancelEditStepOnClick()
}