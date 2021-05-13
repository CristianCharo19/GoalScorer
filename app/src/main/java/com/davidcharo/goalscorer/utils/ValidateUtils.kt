package com.davidcharo.goalscorer.utils

import androidx.core.util.PatternsCompat
import com.google.android.material.textfield.TextInputEditText


fun validateEmail(text: String): Boolean {
    return (PatternsCompat.EMAIL_ADDRESS.matcher(text).matches())
}

fun validatePassword(text: String, minTextSize: Int): Boolean {
    val textSize = text.length
    return textSize >= minTextSize
}




