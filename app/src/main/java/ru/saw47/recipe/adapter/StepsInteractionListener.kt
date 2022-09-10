package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.util.SingleLiveEvent


interface StepsInteractionListener {
    fun editStepOnClick(step: Step)
    fun deleteStepOnClick(step: Step)
    fun saveStepOnClick(step: Step)
    fun addNewStepOnClick(recipe: Recipe)
    fun cancelEditStepOnClick()
    fun deleteStepImageOnClick(step: Step)

    //move
    var tempMovableStep: Step?
    val upDownButtonStateStep: SingleLiveEvent<Boolean>
    fun setMovableStep(step: Step)
    fun showUpDownButtonsStep()
    fun hideUpDownButtonsStep()
    fun moveStep(whereMoveStep: Int)
}