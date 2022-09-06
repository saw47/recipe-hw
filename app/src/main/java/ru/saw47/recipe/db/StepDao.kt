package ru.saw47.recipe.db

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*
import ru.saw47.recipe.data.Category
import ru.saw47.recipe.data.Recipe
import ru.saw47.recipe.data.Step


@Dao
interface StepDao {

    @Query("SELECT * FROM steps ORDER BY stepId DESC")
    fun getSteps(): LiveData<List<StepEntity>>

    @Insert
    fun insertStep(stepEntity: StepEntity)

    @Delete
    fun deleteStep(stepEntity: StepEntity)

    @Update
    fun updateStep(stepEntity: StepEntity)
}