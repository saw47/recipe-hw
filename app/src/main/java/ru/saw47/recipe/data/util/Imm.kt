package ru.saw47.recipe.data.util

import android.content.Context
import android.hardware.input.InputManager
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, /* flags =*/ 0)

}

internal fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, /* flags =*/ 0)
}