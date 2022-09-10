package ru.saw47.recipe.db

import androidx.room.*

@Dao
interface AppDao: RecipeDao, StepDao {

}