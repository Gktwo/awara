package me.rerere.awara.data.source

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

/**
 * The error returned by the API
 *
 * @param status The status code of the error
 * @param message The message of the error
 */
data class APIError(
    val status: Int,
    val message: String,
)

/**
 * Convert the HttpException to APIError
 *
 * @return The converted APIError
 */
fun HttpException.toAPIError(): APIError {
    val body = this.response()?.errorBody()?.string()
    val bodyJson = Json.decodeFromString<JsonObject>(
        body ?: "{}"
    )
    return APIError(
        status = this.code(),
        message = bodyJson["message"]?.jsonPrimitive?.content ?: "error.unknown",
    )
}

/**
 * If the throwable is a HttpException, then convert it to APIError
 *
 * @param block The block to be executed if the throwable is a HttpException
 */
fun Throwable.ifAPIError(block: (APIError) -> Unit) {
    if (this is HttpException) {
        block(this.toAPIError())
    }
}