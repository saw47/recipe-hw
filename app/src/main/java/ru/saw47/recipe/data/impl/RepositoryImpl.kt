package ru.saw47.recipe.data.impl

import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.data.util.Category
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.data.util.Util.fullCheckBox
import ru.saw47.recipe.db.AppDao
import ru.saw47.recipe.db.toEntity
import ru.saw47.recipe.db.toModel

class RepositoryImpl(
    private val dao: AppDao,
) : Repository {

    override val data: MutableLiveData<List<Recipe>> =
        MutableLiveData(dao.getAll().map { it.toModel() })

    override val stepsData: MutableLiveData<List<Step>> =
        MutableLiveData(dao.getSteps().map { it.toModel() })

    private var indexFavorite: Boolean = false
    private var indexString: String = ""
    private var indexCategorySet = fullCheckBox

    private fun refreshRecipeData() {
        var recipes = dao.getAll().map { it.toModel() }
        if (indexFavorite) recipes = recipes.filter { it.isFavorite }
        if (indexString.isNotBlank()) recipes =
            recipes.filter { it.name.contains(indexString).or(it.author.contains(indexString)) }
        if (indexCategorySet != fullCheckBox) recipes = recipes.filter {
            indexCategorySet.contains(it.category)
        }
        data.value = recipes
    }

    private fun refreshStepData() {
        val steps = dao.getSteps().map { it.toModel() }
        stepsData.value = steps
    }

    override fun getFavorite(favorite: Boolean) {
        indexFavorite = favorite
        refreshRecipeData()
    }

    override fun getFilteredByText(text: String) {
        indexString = text
        refreshRecipeData()
    }

    override fun getFilteredBiCategory(set: Set<Category>) {
        indexCategorySet = set
        refreshRecipeData()
    }

    override fun add(recipe: Recipe): Long {
        val newId = dao.insert(recipe.toEntity())
        refreshRecipeData()
        return newId
    }

    override fun delete(recipe: Recipe) {
        dao.delete(recipe.toEntity())
        refreshRecipeData()
    }

    override fun replace(recipe: Recipe) {
        dao.update(recipe.toEntity())
        refreshRecipeData()
    }

    override fun addToFavorite(recipe: Recipe) {
        recipe.id?.let { dao.like(it) }
        refreshRecipeData()
    }

    override fun addNewStep(step: Step) {
        dao.insertStep(step.toEntity())
        refreshStepData()
    }

    override fun deleteStep(step: Step) {
        dao.deleteStep(step.toEntity())
        refreshStepData()
    }

    override fun editStep(step: Step) {
        dao.updateStep(step.toEntity())
        refreshStepData()
    }
}