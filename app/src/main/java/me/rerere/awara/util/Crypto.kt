package me.rerere.awara.util

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun ByteArray.sha1(): ByteArray {
    return java.security.MessageDigest.getInstance("SHA-1").digest(this)
}