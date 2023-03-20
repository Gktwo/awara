package me.rerere.awara.di

import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.util.SerializationConverterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor {
                // TODO: JWT
                val request = it.request()
                val newRequest = request.newBuilder()
                    .addHeader("User-Agent", "Awara/1.0.0")
                    .build()
                it.proceed(newRequest)
            }
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