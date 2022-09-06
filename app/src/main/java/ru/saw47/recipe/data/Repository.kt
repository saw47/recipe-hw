package ru.saw47.recipe.data
import androidx.lifecycle.LiveData

interface Repository {

        //recipe action
        val data: LiveData<List<Recipe>>
        fun add(recipe: Recipe)
        fun delete(recipe: Recipe)
        fun replace(recipe: Recipe)
        fun addToFavorite(recipe: Recipe)

        //recipe steps action
        val stepsData: LiveData<List<Step>>
        fun addNewStep(step: Step)
        fun deleteStep(step: Step)
        fun editStep(step: Step)
}