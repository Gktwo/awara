package me.rerere.awara.di

import android.util.Log
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.util.SerializationConverterFactory
import me.rerere.compose_setting.preference.mmkvPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TAG = "NetworkModule"

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newRequest = request.newBuilder()
                    .addHeader("User-Agent", "Awara Android Client")
                    .apply {
                        if(request.url.toString() == "https://api.iwara.tv/user/token") {
                            if (mmkvPreference.contains("refresh_token")) {
                                addHeader(
                                    "Authorization",
                                    "Bearer ${mmkvPreference.getString("refresh_token", "")}"
                                )
                            }
                        } else {
                            if (mmkvPreference.contains("access_token")) {
                                addHeader(
                                    "Authorization",
                                    "Bearer ${mmkvPreference.getString("access_token", "")}"
                                )
                            }
                        }
                    }
                    .build()
                it.proceed(newRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor {
                    Log.i(TAG, it)
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BASIC)
                }
            )
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
}