package ru.saw47.recipe.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.saw47.recipe.data.Category
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

    private var indexFavorite: Boolean = false
    private var indexString: String = ""
    private var indexCategorySet = fullCheckBox

    private fun refreshData() {
        var recipes = dao.getAll().map { it.toModel() }
        if (indexFavorite) recipes = recipes.filter { it.isFavorite }
        if (indexString.isNotBlank()) recipes =
            recipes.filter { it.name.contains(indexString).or(it.author.contains(indexString)) }
        if (indexCategorySet != fullCheckBox) recipes = recipes.filter {
            indexCategorySet.contains(it.category)
        }
        data.value = recipes
        println("getFavorite - $indexFavorite")
        println("indexString - $indexString")
        println("indexCategorySet = $indexCategorySet")
    }

    //Filter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    override fun getFavorite(favorite: Boolean) {
        indexFavorite = favorite
        refreshData()
    }

    override fun getFilteredByText(text: String) {
        indexString = text
        refreshData()
    }

    override fun getFilteredBiCategory(set: Set<Category>) {
        indexCategorySet = set
        refreshData()
    }
    //<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //CRUD>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    override fun add(recipe: Recipe) {
        dao.insert(recipe.toEntity())
        println("repository add" + recipe.id)
    }

    override fun delete(recipe: Recipe) {
        println("delete in repo id ${recipe.id}")
        dao.delete(recipe.toEntity())
        println("repository del" + recipe.id)
    }

    override fun replace(recipe: Recipe) {
        val r = dao.update(recipe.toEntity())
        println("replace id -" + recipe.id)
        println("replace name -" + recipe.name)
        println("update $r entities")
    }

    override fun addToFavorite(recipe: Recipe) {
        recipe.id?.let { dao.like(it) }
        refreshData()
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // STEPS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    override val stepsData: LiveData<List<Step>>
        get() = dao.getSteps().map { entities ->
            entities.map { it.toModel() }
        }

    override fun addNewStep(step: Step) {
        dao.insertStep(step.toEntity())
    }

    override fun deleteStep(step: Step) {
        dao.deleteStep(step.toEntity())
    }

    override fun editStep(step: Step) {
        dao.updateStep(step.toEntity())
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}