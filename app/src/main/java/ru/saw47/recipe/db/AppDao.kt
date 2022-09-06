package ru.saw47.recipe.db

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Recipe

@Dao
interface AppDao: RecipeDao, StepDao {

}