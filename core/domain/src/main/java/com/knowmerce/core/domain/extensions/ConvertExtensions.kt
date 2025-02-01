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
        val hour = this / 3600
        val minutes = this / 60
        val seconds = this % 60
        return if (hour > 0) {
            "%d:%02d:%02d".format(hour, minutes, seconds)
        } else {
            "%d:%02d".format(minutes, seconds)
        }
    }
}