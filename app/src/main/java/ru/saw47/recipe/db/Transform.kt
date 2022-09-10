package ru.saw47.recipe.db

import androidx.core.net.toUri
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Step

internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    author = author,
    name = name,
    imageUri = imageUri.toUri(),
    category = category,
    isFavorite = isFavorite
)

internal fun Recipe.toEntity() = if (id == null) {
    RecipeEntity(
        author = author,
        name = name,
        imageUri = imageUri.toString(),
        category = category,
        isFavorite = isFavorite
    )
} else {
    RecipeEntity(
        id = id,
        author = author,
        name = name,
        imageUri = imageUri.toString(),
        category = category,
        isFavorite = isFavorite
    )
}


internal fun StepEntity.toModel() = Step(
    id = id,
    parentId = parentId,
    description = description,
    imageUri = imageUri.toUri()
)

internal fun Step.toEntity() = if(id == null){
    StepEntity(
        parentId = parentId,
        description = description,
        imageUri = imageUri.toString()
    )
} else {
    StepEntity(
        id = id,
        parentId = parentId,
        description = description,
        imageUri = imageUri.toString()
    )
}


