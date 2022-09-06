package ru.saw47.recipe.db

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Recipe


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert()
    fun insert(recipe: RecipeEntity)

    @Query("UPDATE recipes SET isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END WHERE id = :id")
    fun like(id: Long)

    @Delete
    fun delete(recipe: RecipeEntity)

    @Update
    fun update(recipe: RecipeEntity): Int

}