package ru.saw47.recipe.data
import androidx.lifecycle.LiveData

interface Repository {

        //recipe action
        val data: LiveData<List<Recipe>>
        fun add(recipe: Recipe)
        fun delete(recipe: Recipe)
        fun save(recipe: Recipe)
        fun filterBy(set: Set<Category>)
        fun searchByName(name: String?)
        fun addToFavorite(recipe: Recipe)
        fun filterByFavorite(onlyFavorite: Boolean)

        //recipe steps action
        val stepsData: LiveData<List<Step>>
        fun getSteps(recipe: Recipe): List<Step>
        fun addNewStep(step: Step)
        fun deleteStep(step: Step)
        fun editStep(step: Step)
}