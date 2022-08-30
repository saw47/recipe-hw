package ru.saw47.recipe.data

import android.net.Uri

data class Recipe(
    val id: Long,
    val author: String,
    val name: String,
    val imageUri:Uri? = null,
    val category: Category = Category.OTHER,
    val steps: List<CookingStep> = mutableListOf(),
    val isFavorite: Boolean = false
)

