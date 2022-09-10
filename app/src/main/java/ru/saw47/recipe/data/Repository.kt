package ru.saw47.recipe.data
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.data.util.Category

interface Repository {

        //recipe action
        fun add(recipe: Recipe): Long
        fun delete(recipe: Recipe)
        fun replace(recipe: Recipe)
        fun addToFavorite(recipe: Recipe)

        val data: MutableLiveData<List<Recipe>>
        fun getFavorite(favorite: Boolean)
        fun getFilteredByText(text: String)
        fun getFilteredBiCategory(set: Set<Category>)

        val stepsData: MutableLiveData<List<Step>>
        fun addNewStep(step: Step)
        fun deleteStep(step: Step)
        fun editStep(step: Step)


}