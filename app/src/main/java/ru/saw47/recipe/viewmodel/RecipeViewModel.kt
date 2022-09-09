package ru.saw47.recipe.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.adapter.AppListener
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Category.*
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.data.impl.RepositoryImpl
import ru.saw47.recipe.data.util.SingleLiveEvent
import ru.saw47.recipe.data.util.Util.fullCheckBox
import ru.saw47.recipe.db.AppDb
import java.lang.Exception

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), AppListener {
    val context = application

    private val repository: Repository = RepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).appDao
    )

    val recipeData
        get() = repository.data

    val stepData
        get() = repository.stepsData


    val expandRecipe = SingleLiveEvent<Recipe>()
    val editRecipe = SingleLiveEvent<Recipe?>()

    val editStep = SingleLiveEvent<Step?>()
    var favoriteIndex = MutableLiveData<Boolean>()
    var checkboxSet = mutableSetOf<Category>()

    fun clearEditRecipeValue() {
        editRecipe.value = null
    }

    fun clearExpandRecipeValue() {
        expandRecipe.value = null
    }

    fun clearEditStepValue() {
        editStep.value = null
    }


    init {
        favoriteIndex.value = false
        checkboxSet = fullCheckBox.toMutableSet()
    }


    override fun favoriteOnClick(recipe: Recipe) {
        repository.addToFavorite(recipe)
    }

    override fun editOnClick(recipe: Recipe) {
        editRecipe.value = recipe
    }

    override fun saveOnClick(recipe: Recipe) {
        if(editRecipe.value == expandRecipe.value) {
            expandRecipe.value = recipe
        }
        if (recipe.id == null) repository.add(recipe) else repository.replace(recipe)
        clearEditRecipeValue()
    }

    override fun addNewOnClick() {
        val newRecipe = Recipe(
            id = null,
            author = "",
            name = ""
        )
        editRecipe.value = newRecipe
    }


    //FILTER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    override fun searchBarOnClick(string: String?) {
        repository.getFilteredByText(string ?: "")
        println("searchBarOnClick string - $string")
    }

    override fun tabBarItemFavoriteClick(itemPosition: Int) {
        favoriteIndex.value = when (itemPosition) {
            0 -> false
            1 -> true
            else -> {
                throw Exception("Tab index out of bound")
            }
        }
        repository.getFavorite(favoriteIndex.value!!)
    }

    override fun filterOnCategoryClick() {
        repository.getFilteredBiCategory(checkboxSet)
    }

    fun skipCheckboxFilter() {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    override fun cancelEditRecipeOnClick() {
        editRecipe.value = null
    }


    //OK
    override fun deleteOnClick(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun frameOnShortClick(recipe: Recipe) {
        expandRecipe.value = recipe
    }

    override fun frameOnLongClick(recipe: Recipe) {
        TODO("Not yet implemented")
    }


    //STEP actions
    override fun cancelEditStepOnClick() {
        editStep.value = null
    }

    override fun deleteStepImageOnClick(step: Step) {
        repository.editStep(
            step.copy(
                imageUri = null
            )
        )
    }

    override fun editStepOnClick(step: Step) {
        editStep.value = step
    }

    override fun deleteStepOnClick(step: Step) {
        repository.deleteStep(step)
    }

    override fun saveStepOnClick(step: Step) {

        if (step.stepId == null) {
            repository.addNewStep(step)
        } else {
            repository.editStep(step)
        }
        editStep.value = null
    }

    override fun addNewStepOnClick(recipe: Recipe) {
        if (recipe.id == null) {
            Toast.makeText(context, "Сначала нужно сохранить рецепт", Toast.LENGTH_SHORT).show()
        } else {
            val newStep = Step(
                parentId = recipe.id,
                stepId = null,
                description = "",
                imageUri = null
            )
            editStep.value = newStep
        }
    }
}