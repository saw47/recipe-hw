package ru.saw47.recipe.data

data class Recipe(
    val id: Long,
    val author: User,
    val name: String,
    val image:String = "DUMMY", //TODO прикрутить дефолтную картинку-заглушку
    val category: Category = Category.OTHER,
    val type: Type = Type.OTHER,
    val steps: List<CookingStep> = mutableListOf()
)

