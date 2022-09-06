package ru.saw47.recipe.data.impl
//
//import android.app.Application
//import androidx.lifecycle.MutableLiveData
//import ru.saw47.recipe.data.*
//import java.lang.Exception
//
//class TestTempRepository(application: Application) : Repository {
//
//    private var recipes: List<Recipe>
//
//    override val data: MutableLiveData<List<Recipe>>
//
//    override val stepsData: MutableLiveData<List<Step>>
//
//    var onlyFavoriteIndex: MutableLiveData<Boolean> = MutableLiveData(false)
//
//    init {
//
//        var steps = listOf<Step>()
//
//        for (i in 0..10) {
//            steps = steps + List(10) { index ->
//                Step(
//                    stepId = index,
//                    description = "RECIPE$index;STEP $i TTTTTTEEEEEXXXTTT",
//                    imageUri = null,
//                    parentId = index.toLong()
//                )
//            }
//        }
//
//        recipes = List(10) { index ->
//            Recipe(
//                id = index.toLong(),
//                author = "$index --${steps.size}-- Randousermuseruser user",
//                name = "$index - Recipe name name name name name name name name name",
//                imageUri = null,
//            )
//        }.toMutableList()
//
//        stepsData = MutableLiveData(steps)
//        data =
//            if (onlyFavoriteIndex.value == true) MutableLiveData(recipes.filter { it.isFavorite }) else MutableLiveData(
//                recipes
//            )
//    }
//
//    companion object {
//        private val categoryTextMap = mapOf<Category, String>(
//            Category.EUROPEAN to "Европейская",
//            Category.ASIAN to "Азиатская",
//            Category.PAN_ASIAN to "Паназиатская",
//            Category.EASTERN to "Восточная",
//            Category.AMERICAN to "Американская",
//            Category.RUSSIAN to "Русская",
//            Category.MEDITERRANEAN to "Средиземноморская",
//            Category.OTHER to "Другая"
//        )
//
//        fun getResourceText(category: Category): String {
//            return categoryTextMap[category]
//                ?: throw Exception("invalid request, no such a category")
//        }
//    }
//
//    override fun add(recipe: Recipe) {
//        println("ADD RECIPE id${recipe.id} name ${recipe.name} author ${recipe.author}")
//    }
//
//    override fun delete(recipe: Recipe) {
//        println("DELETE RECIPE id${recipe.id} name ${recipe.name} author ${recipe.author}")
//    }
//
//    override fun replace(recipe: Recipe) {
//        println("SAVE RECIPE id${recipe.id} name ${recipe.name} author ${recipe.author}")
//    }
//
//    override fun filterBy(set: Set<Category>) {
//        set.forEach {
//            println("FILTER RECIPE CAT $it \n")
//        }
//    }
//
//     fun searchByName(name: String?) {
//        println("PRINT FILTER NAME $name")
//    }
//
//    override fun addToFavorite(recipe: Recipe) {
//        println("ADD TO FAVORITE RECIPE id${recipe.id} name ${recipe.name} author ${recipe.author}")
//        recipes.forEach {
//            if (it.id == recipe.id) {
//                it.isFavorite = !it.isFavorite
//            }
//        }
//        data.value =
//            if (onlyFavoriteIndex.value == true) recipes.filter { it.isFavorite } else recipes
//        println("FAVORITE RECIPE ${recipe.id} -> ${recipe.isFavorite}")
//    }
//
//     fun filterByFavorite(onlyFavorite: Boolean) {
//        println("FILTER BY FAVORITE -> $onlyFavorite")
//        onlyFavoriteIndex.value = onlyFavorite
//        data.value =
//            if (onlyFavoriteIndex.value == true) recipes.filter { it.isFavorite } else recipes
//
//    }
//
//    override fun getSteps(recipe: Recipe): List<Step> {
//        val index = recipe.id
//        return stepsData.value?.filter { it.parentId == index } ?: mutableListOf()
//    }
//
//    override fun addNewStep(step: Step) {
//        stepsData.value = stepsData.value?.plus(step)
//        println("NEW STEP -> parent${step.parentId} id${step.stepId}")
//
//    }
//
//    override fun deleteStep(step: Step) {
//        stepsData.value = stepsData.value?.filterNot { it == step }
//        println("DELETE STEP -> parent${step.parentId} id${step.stepId}")
//    }
//
//    override fun editStep(step: Step) {
//        stepsData.value = stepsData.value?.filterNot { it.stepId == step.stepId && it.parentId == step.parentId }
//        stepsData.value?.toMutableList()?.add(step)
//        println("EDIT STEP -> parent${step.parentId} id${step.stepId}")
//    }
//}