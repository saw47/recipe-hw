package ru.saw47.recipe.adapter

import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.util.SingleLiveEvent


interface RecipeInteractionListener {
    fun favoriteOnClick(recipe: Recipe)
    fun editOnClick(recipe: Recipe)
    fun deleteOnClick(recipe: Recipe)
    fun filterOnCategoryClick()

    fun showUpDownButtons()
    fun hideUpDownButtons()
    val upDownButtonState:SingleLiveEvent<Boolean>
    fun setMovableRecipe(recipe: Recipe)
    fun moveRecipe(whereMove:Int)
    var tempMovableRecipe: Recipe?

    fun searchBarOnClick(string: String?)
    fun frameOnShortClick(recipe: Recipe)
    fun saveOnClick(recipe: Recipe): Long
    fun addNewOnClick()
    fun tabBarItemFavoriteClick(itemPosition: Int)
    fun cancelEditRecipeOnClick()

}