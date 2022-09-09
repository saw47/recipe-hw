package ru.saw47.recipe.data.util

import ru.saw47.recipe.data.Category
import java.lang.Exception

object Util {
    val fullCheckBox = setOf(
        Category.EUROPEAN,
        Category.ASIAN,
        Category.PAN_ASIAN,
        Category.EASTERN,
        Category.AMERICAN,
        Category.RUSSIAN,
        Category.MEDITERRANEAN,
        Category.OTHER
    )

    private val categoryTextMap = mapOf(
        Category.EUROPEAN to "Европейская",
        Category.ASIAN to "Азиатская",
        Category.PAN_ASIAN to "Паназиатская",
        Category.EASTERN to "Восточная",
        Category.AMERICAN to "Американская",
        Category.RUSSIAN to "Русская",
        Category.MEDITERRANEAN to "Средиземноморская",
        Category.OTHER to "Другая"
    )

    fun getResourceText(category: Category): String {
        return categoryTextMap[category]
            ?: throw Exception("invalid request, no such a category")
    }

    fun getCategory(valueIn: String): Category? {
        var category: Category? = null
        categoryTextMap.forEach {
            if (it.value == valueIn) {
                categoryTextMap.entries.forEach { pair ->
                    if (valueIn == pair.value) {
                        category = pair.key
                    }
                }
            }
        }
        return category
    }
}
