package ru.saw47.recipe.data
import androidx.lifecycle.LiveData

interface RecipeRepository {

        val data: LiveData<List<Recipe>>

        fun add(recipe: Recipe)
        fun delete(recipe: Recipe)
        fun save(recipe: Recipe)
        fun filterBy(set: Set<Category>)
        fun searchByName(name: String?)
        fun addToFavorite(recipe: Recipe)

}