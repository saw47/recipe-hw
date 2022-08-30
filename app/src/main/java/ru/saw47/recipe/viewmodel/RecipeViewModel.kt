package ru.saw47.recipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.adapter.RecipeInteractionListener
import ru.saw47.recipe.adapter.RecipeStepsListener
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Category.*
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.impl.TestTempRepository
import ru.saw47.recipe.data.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener, RecipeStepsListener {

    private val repository = TestTempRepository(application)
    val data get() = repository.data

    private val fullCheckBox = setOf(
        EUROPEAN, ASIAN, PAN_ASIAN, EASTERN,
        AMERICAN, RUSSIAN, MEDITERRANEAN, OTHER
    )

    var checkboxSet = mutableSetOf<Category>()
    val editRecipe = SingleLiveEvent<Recipe>()
    val expandRecipe = SingleLiveEvent<Recipe>()

    init {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun skipCheckboxFilter() {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    override fun favoriteOnClick(recipe: Recipe) {
        repository.addToFavorite(recipe)
    }

    override fun editOnClick(recipe: Recipe) {
        editRecipe.value = recipe

    }

    override fun saveOnClick(recipe: Recipe) {
        repository.save(recipe)
    }

    override fun deleteOnClick(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun filterOnClick() {
        repository.filterBy(checkboxSet)
    }

    override fun searchBarOnClick(string: String) {
        repository.searchByName(string)
    }

    override fun frameOnShortClick(recipe: Recipe) {
        expandRecipe.value = recipe
    }

    override fun frameOnLongClick(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun editStepOnClick(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun deleteStepOnClick(recipe: Recipe) {
        TODO("Not yet implemented")
    }


}