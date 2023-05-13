package com.arrazyfathan.common_utils.extensions

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.arrazyfathan.common_utils.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by Ar Razy Fathan Rabbani on 11/05/23.
 */

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
        .load(url)
        .placeholder(circularProgressDrawable)
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
