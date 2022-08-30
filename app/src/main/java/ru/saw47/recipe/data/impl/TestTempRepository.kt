package ru.saw47.recipe.data.impl

import android.app.Application
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.saw47.recipe.data.*
import java.lang.Exception

class TestTempRepository(application: Application) : RecipeRepository {

    private var recipes
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }
    override val data: MutableLiveData<List<Recipe>>
    init {
        val steps = List(10) { index ->
            CookingStep(
                step = index,
                description = "$index - dfddddddddddddddddddddddddddddddddddddddddddddd",
                imageUri = null
            )
        }
        val recipes = List(100) { index ->
            Recipe(
                id = index.toLong(),
                author = "$index --${steps.size}-- Randousermuseruser user",
                name = "$index - Recipe name name name name name name name name name",
                imageUri = null,
                steps = steps
            )
        }.toMutableList()

        data = MutableLiveData(recipes)
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
            return categoryTextMap[category] ?: throw Exception("invalid request, no such a category")
        }
    }

    override fun add(recipe: Recipe) {
      return
    }

    override fun delete(recipe: Recipe) {
        return
    }

    override fun save(recipe: Recipe) {
        return
    }

    override fun filterBy(set: Set<Category>) {
        return
    }

    override fun searchByName(name: String?) {
        return
    }

    override fun addToFavorite(recipe: Recipe) {
       return
    }
}