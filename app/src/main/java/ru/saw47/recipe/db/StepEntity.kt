package ru.saw47.recipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "steps")
class StepEntity(
    @ColumnInfo(name = "parentId")
    val parentId: Long,
    @ColumnInfo(name = "stepId")
    val stepId: Int,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imageUri")
    val imageUri: String?
)