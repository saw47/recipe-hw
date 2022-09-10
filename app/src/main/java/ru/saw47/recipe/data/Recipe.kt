package ru.saw47.recipe.data

import android.net.Uri
import ru.saw47.recipe.data.util.Category

data class Recipe(
    override val id: Long?,
    val author: String,
    val name: String,
    val imageUri:Uri? = null,
    val category: Category = Category.OTHER,
    var isFavorite: Boolean = false
): Item()

