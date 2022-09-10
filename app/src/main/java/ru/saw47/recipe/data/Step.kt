package ru.saw47.recipe.data

import android.net.Uri

data class Step(
    override val id: Long?,
    val parentId: Long,
    val description: String,
    val imageUri: Uri? = null
): Item()