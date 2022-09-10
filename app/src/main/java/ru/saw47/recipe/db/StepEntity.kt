package ru.saw47.recipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "steps", foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parentId"),
        onDelete = CASCADE,
    )]
)
class StepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "parentId")
    val parentId: Long,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imageUri")
    val imageUri: String
)