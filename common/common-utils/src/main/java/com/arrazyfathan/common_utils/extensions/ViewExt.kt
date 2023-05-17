package com.arrazyfathan.common_utils.extensions

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.arrazyfathan.common_utils.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

/**
 * Created by Ar Razy Fathan Rabbani on 11/05/23.
 */

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun Fragment.hideKeyboard(view: View) {
    val inputMethodManager =
        context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.focusAndShowKeyboard(view: View) {
    if (view.isFocusable) {
        view.requestFocus()
    }
    if (view is EditText) {
        showKeyboard()
    }
}

fun Fragment.showKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Fragment.showKeyboard(editText: View) {
    editText.requestFocus()
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Activity.showKeyboard(editText: View) {
    editText.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun showKeyboard(context: Context, editText: View) {
    editText.requestFocus()
    editText.postDelayed({
        val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(editText, 0)
    }, 200)
}

fun hideSoftKeyboard(context: Context, editText: View) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(this)
        .load(url.ifBlank { R.drawable.no_image })
        .placeholder(circularProgressDrawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transform(CenterCrop(), RoundedCorners(16))
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(com.google.android.material.R.drawable.mtrl_ic_error).into(this)
}

fun showCustomToast(message: String, activity: Activity) {
    val inflater = activity.layoutInflater
    val layout = inflater.inflate(R.layout.custom_toast_layout, null)

    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    Toast(activity).apply {
        setGravity(Gravity.TOP, 0, 20)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}

fun MaterialCardView.setOnOneTimeClickListener(onClick: () -> Unit) {
    val wrapperOnClickListener = object : View.OnClickListener {
        private var clicked = false

        override fun onClick(v: View?) {
            if (!clicked) {
                clicked = true
                onClick()
            }
        }
    }

    setOnClickListener(wrapperOnClickListener)
}
