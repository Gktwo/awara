package me.rerere.awara.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class SerializationConverterFactory(private val json: Json) : Converter.Factory() {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        fun create(json: Json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
            prettyPrint = true
        }): SerializationConverterFactory = SerializationConverterFactory(json)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val serializer = serializer(type)
        return SerializationResponseBodyConverter(json, serializer)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val serializer = serializer(type)
        return SerializationRequestBodyConverter(json, serializer)
    }
}


internal class SerializationRequestBodyConverter<T>(
    private val json: Json,
    private val type: KSerializer<T>
) : Converter<T, RequestBody> {
    companion object {
        private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaTypeOrNull()
    }

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val string = json.encodeToString(type, value)
        return string.toRequestBody(MEDIA_TYPE)
    }
}

internal class SerializationResponseBodyConverter<T>(
    private val json: Json,
    private val type: KSerializer<T>
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(body: ResponseBody): T {
        val string = body.string()
        return json.decodeFromString(type, string)
    }
}