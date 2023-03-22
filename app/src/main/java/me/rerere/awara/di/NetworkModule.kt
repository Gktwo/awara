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
                    .addHeader("User-Agent", "Awara/1.0.0")
                    .apply {
                        if (mmkvPreference.contains("token")) {
                            addHeader(
                                "Authorization",
                                "Bearer ${mmkvPreference.getString("token", "")}"
                            )
                            // Log.i(TAG, "Auth: Bearer ${mmkvPreference.getString("token", "")}")
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
    }

    single {
        get<Retrofit>().create(IwaraAPI::class.java)
    }
}