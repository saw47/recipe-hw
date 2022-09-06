package ru.saw47.recipe.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ru.saw47.recipe.adapter.AppListener
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Category.*
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.data.impl.RepositoryImpl
import ru.saw47.recipe.data.util.SingleLiveEvent
import ru.saw47.recipe.db.AppDb
import java.lang.Exception

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), AppListener {

    var favoriteIndex = false
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

    fun clearEditRecipeValue() {
        editRecipe.value = null
    }

    val editStep = SingleLiveEvent<Step?>()

    fun clearEditStepValue() {
        editStep.value = null
    }

    private val fullCheckBox = setOf(
        EUROPEAN, ASIAN, PAN_ASIAN, EASTERN,
        AMERICAN, RUSSIAN, MEDITERRANEAN, OTHER
    )

    var checkboxSet = mutableSetOf<Category>()

    init {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun skipCheckboxFilter() {
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun getStepsList(recipe: Recipe): List<Step> {
        return stepData.value?.filter { it.parentId == recipe.id }?.sortedBy { it.stepId }
            ?: emptyList()
    }

    override fun favoriteOnClick(recipe: Recipe) {
        repository.addToFavorite(recipe)
    }

    override fun editOnClick(recipe: Recipe) {
        editRecipe.value = recipe
        println("viewmodel edit value id - ${recipe.id} name - ${recipe.name}")
    }

    override fun saveOnClick(recipe: Recipe) {
        if (recipe.id == null) repository.add(recipe) else repository.replace(recipe)
    }

    override fun addNewOnClick() {
        val newRecipe = Recipe(
            id = null,
            author = "",
            name = ""
        )
        editRecipe.value = newRecipe
    }

    override fun tabBarItemClick(itemPosition: Int) {
        favoriteIndex = when (itemPosition) {
            0 -> false
            1 -> true
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
        if (checkboxSet != fullCheckBox) {
            //TODO noimpl
        } else {
            skipCheckboxFilter()
            //TODO noimpl
        }
    }

    override fun searchBarOnClick(string: String) {
        //TODO no impl
    }

    //OK
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
    }

    override fun addNewStepOnClick(recipe: Recipe) {
        if(recipe.id == null) {
            Toast.makeText(context, "Сначала нужно сохранить рецепт", Toast.LENGTH_SHORT).show()
        } else{
            val newStep = Step(
                    parentId = recipe.id,
                    stepId = null,
                    description = "",
                    imageUri = null
                )
            editStep.value = newStep
        }
    }

    companion object {
        private val categoryTextMap = mapOf<Category, String>(
            Category.EUROPEAN to "Европейская",
            Category.ASIAN to "Азиатская",
            Category.PAN_ASIAN to "Паназиатская",
            Category.EASTERN to "Восточная",
            Category.AMERICAN to "Американская",
            Category.RUSSIAN to "Русская",
            Category.MEDITERRANEAN to "Средиземноморская",
            Category.OTHER to "Другая"
        )

        fun getResourceText(category: Category): String {
            return categoryTextMap[category]
                ?: throw Exception("invalid request, no such a category")
        }
    }
}