package com.arrazyfathan.common_utils.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */

fun Int.dp(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}