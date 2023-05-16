package com.arrazyfathan.common_utils.extensions

import android.content.Context
import android.util.TypedValue

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */

fun Int.toDp(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}