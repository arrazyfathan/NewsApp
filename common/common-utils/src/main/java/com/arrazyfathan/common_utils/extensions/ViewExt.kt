package com.arrazyfathan.common_utils.extensions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by Ar Razy Fathan Rabbani on 11/05/23.
 */

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
