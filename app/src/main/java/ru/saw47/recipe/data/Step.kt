package ru.saw47.recipe.data

data class Step(
    val parentId: Long,
    val stepId: Int,
    val description: String,
    val imageUri: String?
)