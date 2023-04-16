package me.rerere.awara.data.source

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.rerere.awara.R
import retrofit2.HttpException
import java.util.UUID

private const val TAG = "APIResult"

sealed class APIResult<T> {
    data class Success<T>(val data: T) : APIResult<T>()

    data class Error(
        val status: Int,
        val message: String,
    ) : APIResult<Nothing>()

    data class Exception(
        val exception: Throwable,
    ) : APIResult<Nothing>()
}

suspend fun <T> runAPICatching(block: suspend () -> T): APIResult<out T> {
    return runCatching {
        APIResult.Success(block())
    }.getOrElse {
        when (it) {
            is HttpException -> it.toAPIError()
            else -> APIResult.Exception(it)
        }
    }
}

inline fun <T> APIResult<T>.onSuccess(block: (T) -> Unit): APIResult<T> {
    if (this is APIResult.Success) {
        block(data)
    }
    return this
}

inline fun <T> APIResult<T>.onError(block: (APIResult.Error) -> Unit): APIResult<T> {
    if (this is APIResult.Error) {
        block(this)
    }
    return this
}

inline fun <T> APIResult<T>.onException(block: (APIResult.Exception) -> Unit): APIResult<T> {
    if (this is APIResult.Exception) {
        block(this)
    }
    return this
}

private fun HttpException.toAPIError(): APIResult.Error {
    val body = this.response()?.errorBody()?.string()
    val bodyJson = kotlin.runCatching {
        Json.decodeFromString<JsonObject>(
            body ?: "{}"
        )
    }.getOrElse {
        JsonObject(emptyMap())
    }
    return APIResult.Error(
        status = this.code(),
        message = bodyJson["message"]?.jsonPrimitive?.content ?: "error",
    )
}

fun Context.stringResourceOfError(error: APIResult.Error): String {
    return when(error.message) {
        "errors.notFound" -> resources.getString(R.string.errors_not_found)
        "errors.validationError" -> resources.getString(R.string.errors_validation_error)
        "errors.invalidLogin" -> resources.getString(R.string.errors_invalid_login)
        else -> resources.getString(R.string.errors_unknown, error.message)
    }
}

@Composable
@ReadOnlyComposable
fun stringResource(error: APIResult.Error): String {
    val context = LocalContext.current
    LocalConfiguration.current
    return context.stringResourceOfError(error)
}