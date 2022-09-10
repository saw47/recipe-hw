package ru.saw47.recipe.db

import androidx.room.*


@Dao
interface StepDao {

    @Query("SELECT * FROM steps ORDER BY id DESC")
    fun getSteps(): List<StepEntity>

    @Insert
    fun insertStep(stepEntity: StepEntity)

    @Delete
    fun deleteStep(stepEntity: StepEntity)

    @Update
    fun updateStep(stepEntity: StepEntity)
}