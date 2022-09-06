package ru.saw47.recipe.data

import android.net.Uri

data class Step(
    val stepId: Int?,
    val parentId: Long,
    val description: String,
    val imageUri: Uri? = null
)