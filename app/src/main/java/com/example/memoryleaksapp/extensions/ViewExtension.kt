package com.example.memoryleaksapp.extensions

import android.view.View
/**
 * Переводит View в состояние VISIBLE
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Переводит View в состояние INVISIBLE
 */
fun View.hide() {
    visibility = View.INVISIBLE
}

/**
 * Переводит View в состояние GONE
 */
fun View.gone() {
    visibility = View.GONE
}
