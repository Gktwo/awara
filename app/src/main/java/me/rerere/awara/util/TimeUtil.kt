package me.rerere.awara.util

import java.text.DateFormat
import java.time.Instant
import java.util.Date

/**
 * Convert [Instant] to [Date]
 *
 * @return [Date]
 */
fun Instant.toDate(): Date {
    return Date.from(this)
}

/**
 * Convert [Instant] to local date time string
 *
 * @return local date time string
 */
fun Instant.toLocalDateTimeString(): String {
    return DateFormat.getDateTimeInstance().format(this.toDate())
}

/**
 * Convert [Instant] to local date string
 *
 * @return local date string
 */
fun Instant.toLocalDateString(): String {
    return DateFormat.getDateInstance().format(this.toDate())
}