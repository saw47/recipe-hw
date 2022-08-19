package ru.saw47.recipe.data
import androidx.lifecycle.LiveData

interface RecipeRepository {

        val data: LiveData<List<Recipe>>

        fun add(recipe: Recipe)
        fun delete(recipe: Recipe)
        fun edit(recipe: Recipe)

        fun filterBy(id: Long? = null,
                     author: String? = null,
                     category: Category? = null,
                     type: Type? = null
                     )

        fun searchByName(name: String?)

}