package io.keepcoding.eh_ho.utils

import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

const val PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

fun View.showError(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
}

fun CharSequence?.isValidPassword() = !isNullOrEmpty() && Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
