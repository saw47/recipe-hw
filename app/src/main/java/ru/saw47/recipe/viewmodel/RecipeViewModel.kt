package ru.saw47.recipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.saw47.recipe.adapter.InteractionListener
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.impl.RoomRecipeRepository

class RecipeViewModel (application: Application
) : AndroidViewModel(application), InteractionListener {

        private val repository = RoomRecipeRepository(application)
        val data get() = repository.data

        override fun shareOnClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }

        override fun editOnClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }

        override fun deleteOnClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }

        override fun filterOnClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }

        override fun frameOnShortClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }

        override fun frameOnLongClick(recipe: Recipe) {
                TODO("Not yet implemented")
        }


}