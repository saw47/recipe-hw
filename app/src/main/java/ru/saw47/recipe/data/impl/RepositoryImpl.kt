package ru.saw47.recipe.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.db.AppDao
import ru.saw47.recipe.db.toEntity
import ru.saw47.recipe.db.toModel

class RepositoryImpl(private val dao: AppDao,

) : Repository {

    override val data: LiveData<List<Recipe>>
        get() = dao.getAll().map { entities ->
            entities.map { it.toModel()}
        }



    override val stepsData: LiveData<List<Step>>
        get() = dao.getSteps().map { entities ->
            entities.map { it.toModel() }
        }

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
        dao.getAll().map { entities ->
            entities.map { it.toModel()}
        }.value?.forEach { println("id - ${it.id}\n") }?: println("net ID mfc")
    }

    override fun addToFavorite(recipe: Recipe) {
        recipe.id?.let { dao.like(it) }
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
}