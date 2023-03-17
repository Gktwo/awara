package me.rerere.awara.data.source

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

data class APIError(
    val status: Int,
    val message: String,
)

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

fun Throwable.ifAPIError(block: (APIError) -> Unit) {
    if (this is HttpException) {
        block(this.toAPIError())
    }
}