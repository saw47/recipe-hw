package ru.saw47.recipe.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.adapter.AppListener
import ru.saw47.recipe.data.util.Category
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

    val expandRecipe = SingleLiveEvent<Recipe?>()
    val editRecipe = SingleLiveEvent<Recipe?>()
    val editStep = SingleLiveEvent<Step?>()
    var favoriteIndex = MutableLiveData<Boolean>()
    var checkboxSet = mutableSetOf<Category>()


    override var tempMovableStep: Step? = null
    override val upDownButtonStateStep = SingleLiveEvent<Boolean>()
    override fun setMovableStep(step: Step) {
        tempMovableStep = step
    }
    override fun showUpDownButtonsStep() {
        upDownButtonStateStep.value = true
    }
    override fun hideUpDownButtonsStep() {
        upDownButtonStateStep.value = false
    }

    override fun moveStep(whereMoveStep: Int) {
        if (tempMovableStep == null) return
        var item = tempMovableStep
        val indexMap = stepData.value?.filter { it.parentId == tempMovableStep!!.parentId }?.map { it.id }
            ?.sortedBy { it }
        val itemIndex = indexMap!!.indexOf(item!!.id)
        val upAvailable: Boolean = indexMap.lastIndex > itemIndex
        val downAvailable: Boolean = itemIndex > 0
        if (whereMoveStep == 1 && !upAvailable) return
        if (whereMoveStep == -1 && !downAvailable) return
        var temporalItem = stepData.value!!.find { it.id == indexMap[itemIndex + whereMoveStep] }
        temporalItem = temporalItem!!.copy(id = indexMap[itemIndex])
        item = item.copy(id = indexMap[itemIndex + whereMoveStep])
        repository.editStep(temporalItem)
        repository.editStep(item)
        tempMovableStep = item
    }


    override var tempMovableRecipe: Recipe? = null
    override val upDownButtonState = SingleLiveEvent<Boolean>()
    override fun setMovableRecipe(recipe: Recipe) {
        tempMovableRecipe = recipe
    }
    override fun showUpDownButtons() {
        upDownButtonState.value = true
    }
    override fun hideUpDownButtons() {
        upDownButtonState.value = false
    }
    override fun moveRecipe(whereMove: Int) {
        if (tempMovableRecipe == null) return
        var item = tempMovableRecipe
        val indexMap = recipeData.value?.map { it.id }?.sortedBy { it }
        val itemIndex = indexMap!!.indexOf(item!!.id)
        val upAvailable: Boolean = indexMap.lastIndex > itemIndex
        val downAvailable: Boolean = itemIndex > 0
        if (whereMove == 1 && !upAvailable) return
        if (whereMove == -1 && !downAvailable) return
        var temporalItem = recipeData.value!!.find { it.id == indexMap[itemIndex + whereMove] }
        temporalItem = temporalItem!!.copy(id = indexMap[itemIndex])
        item = item.copy(id = indexMap[itemIndex + whereMove])
        repository.replace(temporalItem)
        repository.replace(item)
        tempMovableRecipe = item
    }

    init {
        tempMovableStep = null
        upDownButtonStateStep.value = false

        tempMovableRecipe = null
        upDownButtonState.value = false

        favoriteIndex.value = false
        checkboxSet = fullCheckBox.toMutableSet()
    }

    fun clearEditRecipeValue() {
        editRecipe.value = null
    }

    fun clearExpandRecipeValue() {
        expandRecipe.value = null
    }

    override fun favoriteOnClick(recipe: Recipe) {
        repository.addToFavorite(recipe)
    }

    override fun editOnClick(recipe: Recipe) {
        editRecipe.value = recipe
    }

    override fun saveOnClick(recipe: Recipe): Long {
        if (editRecipe.value == expandRecipe.value) {
            expandRecipe.value = recipe
        }

        val newId: Long = if (recipe.id == null) {
            repository.add(recipe)
        } else {
            repository.replace(recipe)
            recipe.id
        }
        return newId
    }

    override fun addNewOnClick() {
        val newRecipe = Recipe(
            id = null,
            author = "",
            name = ""
        )
        editRecipe.value = newRecipe
    }


    override fun searchBarOnClick(string: String?) {
        repository.getFilteredByText(string ?: "")
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

    override fun cancelEditRecipeOnClick() {
        editRecipe.value = null
    }


    override fun deleteOnClick(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun frameOnShortClick(recipe: Recipe) {
        expandRecipe.value = recipe
    }

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
        if (step.id == null) {
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
                id = null,
                description = "",
                imageUri = null
            )
            editStep.value = newStep
        }
    }
}