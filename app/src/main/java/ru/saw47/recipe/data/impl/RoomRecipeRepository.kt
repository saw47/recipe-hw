package ru.saw47.recipe.data.impl

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.RecipeRepository
import ru.saw47.recipe.data.Type
import kotlin.properties.Delegates

class RoomRecipeRepository(private val application: Application) : RecipeRepository {

    override val data: LiveData<List<Recipe>>
        get() = TODO("Not yet implemented")

    override fun add(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun delete(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun edit(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun filterBy(id: Long?, author: String?, category: Category?, type: Type?) {
        TODO("Not yet implemented")
    }

    override fun searchByName(name: String?) {
        TODO("Not yet implemented")
    }


}