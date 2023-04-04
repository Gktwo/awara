package me.rerere.awara.util

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Await for the response
 *
 * @return The response
 */
suspend fun Call.await() = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }
    enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: okhttp3.Response) {
            continuation.resume(response)
        }
    })
}