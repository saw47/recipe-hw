package ru.saw47.recipe.db

import androidx.core.net.toUri
import ru.saw47.recipe.data.Recipe

internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    author = author,
    name = name,
    imageUri = imageUri.toUri(),
    category = category,
    isFavorite = isFavorite
)

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    author = author,
    name = name,
    imageUri = imageUri.toString(),
    category = category,
    isFavorite = isFavorite
)

