package com.dicoding.submissionjetpackcompose.ui.common

sealed class UiStates<out T: Any?> {
    object Loading: UiStates<Nothing>()
    data class Success<out T: Any>(val data: T) : UiStates<T>()
    data class Error(val messageError: String) : UiStates<Nothing>()

}