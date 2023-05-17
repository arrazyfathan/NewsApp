package com.arrazyfathan.common_utils

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.time.Instant

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