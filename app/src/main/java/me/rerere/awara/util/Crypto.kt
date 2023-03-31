package me.rerere.awara.util

private val sha1 by lazy {
    java.security.MessageDigest.getInstance("SHA-1")
}

private val sha256 by lazy {
    java.security.MessageDigest.getInstance("SHA-256")
}

/**
 * Convert a byte array to hex string
 *
 * @return hex string
 */
fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

/**
 * Calculate sha1 of a byte array
 *
 * @return sha1 result
 */
fun ByteArray.sha1(): ByteArray {
    return sha1.digest(this)
}

/**
 * Calculate sha256 of a byte array
 *
 * @return sha256 result
 */
fun ByteArray.sha256(): ByteArray {
    return sha256.digest(this)
}