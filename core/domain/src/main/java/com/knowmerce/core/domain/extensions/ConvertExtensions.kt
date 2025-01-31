package com.knowmerce.core.domain.extensions

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object ConvertExtensions {
    fun String.toDateTime(): String {
        val dt = OffsetDateTime.parse(this)
        val formatter = DateTimeFormatter.ofPattern("yyyy.M.d")
        return dt.format(formatter)
    }

    fun Int.toPlayTime(): String {
        val minutes = this / 60
        val seconds = this % 60
        return "%d:%02d".format(minutes, seconds)
    }
}