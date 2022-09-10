package ru.saw47.recipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.saw47.recipe.data.util.Category

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "imageUri")
    val imageUri: String,
    @ColumnInfo(name = "category")
    val category: Category,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)



