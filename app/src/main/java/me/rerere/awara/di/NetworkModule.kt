package me.rerere.awara.di

import me.rerere.awara.data.source.HitokotoAPI
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.data.source.UpdateChecker
import me.rerere.awara.util.SerializationConverterFactory
import me.rerere.compose_setting.preference.mmkvPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TAG = "NetworkModule"
private const val UA = "Mozilla/5.0 (Linux; Android 12; Pixel 6 Build/SD1A.210817.023; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/94.0.4606.71 Mobile Safari/537.36"

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor {
                val request = it.request()
                val url = request.url
                val newRequest = request.newBuilder()
                    .apply {
                        if(url.host.contains("iwara.tv")) {
                            addHeader("User-Agent", UA)
                            addHeader("Origin", "https://www.iwara.tv")
                            addHeader("Referer", "https://www.iwara.tv/")
                            addHeader("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")

                            if (url.toString() == "https://api.iwara.tv/user/token") {
                                if ("refresh_token" in mmkvPreference) {
                                    addHeader(
                                        "Authorization",
                                        "Bearer ${mmkvPreference.getString("refresh_token", "")}"
                                    )
                                }
                            } else {
                                if ("access_token" in mmkvPreference) {
                                    addHeader(
                                        "Authorization",
                                        "Bearer ${mmkvPreference.getString("access_token", "")}"
                                    )
                                }
                            }
                        }
                    }
                    .build()
                it.proceed(newRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
//            .addInterceptor {
//                val request = it.request()
//                val url = request.url
//                if(url.pathSegments.contains("video")){
//                    // 403模拟
//                    val response = okhttp3.Response.Builder()
//                        .request(request)
//                        .protocol(okhttp3.Protocol.HTTP_1_1)
//                        .code(403)
//                        .message("Forbidden")
//                        .body(
//                            "".toResponseBody()
//                        )
//                        .build()
//                    response
//                } else {
//                    it.proceed(request)
//                }
//            }
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.iwara.tv")
            .addConverterFactory(SerializationConverterFactory.create())
            .build()
            .create(IwaraAPI::class.java)
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://v1.hitokoto.cn")
            .addConverterFactory(SerializationConverterFactory.create())
            .build()
            .create(HitokotoAPI::class.java)
    }

    single {
        UpdateChecker(get())
    }
}