package com.arrazyfathan.common_utils

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