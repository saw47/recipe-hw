package ru.saw47.recipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.saw47.recipe.adapter.RecipeInteractionListener
import ru.saw47.recipe.adapter.StepsInteractionListener
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Category.*
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.data.impl.RepositoryImpl
import ru.saw47.recipe.data.impl.TestTempRepository
import ru.saw47.recipe.data.util.SingleLiveEvent
import ru.saw47.recipe.db.AppDb
import ru.saw47.recipe.db.RecipeDao
import java.lang.Exception

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener, StepsInteractionListener {


    private val repository: Repository = RepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao
    )

    val data get() = repository.data
    val stepData get() = repository.stepsData

 //   var favoriteIndex = repository.onlyFavoriteIndex
    var favoriteIndex = 1


    private val fullCheckBox = setOf(
        EUROPEAN, ASIAN, PAN_ASIAN, EASTERN,
        AMERICAN, RUSSIAN, MEDITERRANEAN, OTHER
    )

    var checkboxSet = mutableSetOf<Category>()

    val editRecipe = SingleLiveEvent<Recipe?>()
    fun clearEditRecipeValue(){
        editRecipe.value = null
    }

    val editStep = SingleLiveEvent<Step?>()

    val expandRecipe = SingleLiveEvent<Recipe>()
    init {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun skipCheckboxFilter() {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun getStepsList(recipe: Recipe): List<Step> {
        return repository.getSteps(recipe)
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

    override fun addNewOnClick() {
        val newRecipe = Recipe(
            id = 0,
            author = "",
            name = ""
        )
        editRecipe.value = newRecipe
    }

    override fun tabBarItemClick(itemPosition: Int) {
        when (itemPosition) {
            0 -> repository.filterByFavorite(false)
            1 -> repository.filterByFavorite(true)
            else -> throw Exception("Incorrect index")
        }
    }

    override fun canceEditRecipelOnClick() {
        editRecipe.value = null
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



    //STEP actions
    override fun cancelEditStepOnClick() {
        editStep.value = null
    }

    override fun editStepOnClick(step: Step) {
        editStep.value = step
    }

    override fun deleteStepOnClick(step: Step) {
        repository.deleteStep(step)
    }

    override fun saveStepOnClick(step: Step) {
        if (step.stepId == -1) {
            repository.addNewStep(step)
        } else {
            repository.editStep(step)
        }
    }

//    fun getSteps(recipe: Recipe): List<CookingStep>
//    fun addNewStep(step: CookingStep)
//    fun deleteStep(step: CookingStep)
//    fun editStep(step: CookingStep)

    override fun addNewStepOnClick(recipe: Recipe) {
        val newStep = Step(
            parentId = recipe.id ?: 0,  //сделать нормальное создание
            stepId = -1,
            description = "",
            imageUri = null
        )
        editStep.value = newStep
    }
}