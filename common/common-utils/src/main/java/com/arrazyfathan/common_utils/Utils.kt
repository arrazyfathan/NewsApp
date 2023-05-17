package com.arrazyfathan.common_utils

import android.os.Handler
import android.os.Looper
import android.text.Spanned
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.time.Instant
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

/**
 * Created by Ar Razy Fathan Rabbani on 12/05/23.
 */

fun dateTimeAgo(date: String): String {
    val instant = Instant.parse(date)
    val ms = instant.toEpochMilli()
    return TimeAgo.using(ms).replaceFirstChar { it.uppercaseChar() }
}

fun getFirstLetterSource(source: String): Char {
    return source[0].uppercaseChar()
}

fun formatHtmlText(htmlString: String): Spanned {
    return HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

private var doubleBackToExitPressedOnce = false

fun enableDoubleTapToExit(activity: AppCompatActivity) {
    activity.onBackPressedDispatcher.addCallback(
        activity,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity.finish()
                } else {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000L)
                }
            }
        }
    )
}

fun getDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    val days = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    return days[dayOfWeek - 1]
}
