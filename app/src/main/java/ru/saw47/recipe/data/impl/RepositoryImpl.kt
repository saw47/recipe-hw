package ru.saw47.recipe.data.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Step
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Repository
import ru.saw47.recipe.db.RecipeDao
import ru.saw47.recipe.db.toEntity
import ru.saw47.recipe.db.toModel

class RepositoryImpl(private val dao: RecipeDao,

) : Repository {

    private val recipes
       get() = checkNotNull(data.value) {
       }

    override val data: LiveData<List<Recipe>>
        get() = dao.getall().map { entities ->
            entities.map { it.toModel()}
        }

    override val stepsData: LiveData<List<Step>>
        get() = TODO("Not yet implemented")


    override fun add(recipe: Recipe) {
        //логика создания новоо рецепта, сохранениня нет, для это го саве
    }

    override fun delete(recipe: Recipe) {
        dao.delete(recipe.toEntity())
    }

    override fun save(recipe: Recipe) {
        dao.insert(recipe.toEntity())
    }

    override fun filterBy(set: Set<Category>) {
        TODO("Not yet implemented")
    }


    override fun searchByName(name: String?) {
        //Not yet implemented
    }

    override fun addToFavorite(recipe: Recipe) {
        dao.like(recipe.id)
    }

    override fun filterByFavorite(onlyFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getSteps(recipe: Recipe): List<Step> {
        return emptyList()
    }

    override fun addNewStep(step: Step) {
        TODO("Not yet implemented")
    }

    override fun deleteStep(step: Step) {
        TODO("Not yet implemented")
    }

    override fun editStep(step: Step) {
        TODO("Not yet implemented")
    }


}