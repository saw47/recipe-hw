package ru.saw47.recipe.db

import androidx.room.*


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavorite(): List<RecipeEntity>

    @Insert()
    fun insert(recipe: RecipeEntity): Long

    @Query("UPDATE recipes SET isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END WHERE id = :id")
    fun like(id: Long)

    @Delete
    fun delete(recipe: RecipeEntity)

    @Update
    fun update(recipe: RecipeEntity): Int

}