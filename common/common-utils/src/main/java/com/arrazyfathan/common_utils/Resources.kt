package com.arrazyfathan.common_utils

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
sealed class Resources<T> {
    class Loading<T>() : Resources<T>()
    class Success<T>(val data: T) : Resources<T>()
    class Error<T>(val message: String, val data: T? = null) : Resources<T>()
}
