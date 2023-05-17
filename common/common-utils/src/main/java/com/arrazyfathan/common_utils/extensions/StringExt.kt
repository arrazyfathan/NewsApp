package com.arrazyfathan.common_utils.extensions

import com.google.gson.Gson

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */

inline fun <reified T : Any> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T : Any> String.fromJson(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}