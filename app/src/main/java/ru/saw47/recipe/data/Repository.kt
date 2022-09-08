package ru.saw47.recipe.data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import ru.saw47.recipe.db.RecipeEntity

interface Repository {

        //recipe action
        fun add(recipe: Recipe)
        fun delete(recipe: Recipe)
        fun replace(recipe: Recipe)
        fun addToFavorite(recipe: Recipe)

        //recipe filter & get value
        val data: MutableLiveData<List<Recipe>>
        fun getFavorite(favorite: Boolean)
        fun getFilteredByText(text: String)
        fun getFilteredBiCategory(set: Set<Category>)

        //recipe steps action
        val stepsData: LiveData<List<Step>>
        fun addNewStep(step: Step)
        fun deleteStep(step: Step)
        fun editStep(step: Step)


}