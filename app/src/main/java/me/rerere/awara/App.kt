package me.rerere.awara

import android.app.Application
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.APIError
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.ui.page.index.IndexVM
import me.rerere.awara.ui.page.login.LoginVM
import me.rerere.awara.util.SerializationConverterFactory
import me.rerere.compose_setting.preference.initComposeSetting
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initComposeSetting()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                repoModule,
                viewModelModule
            )
        }
    }
}

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

val repoModule = module {
    single {
        UserRepo(get())
    }
}

val viewModelModule = module {
    viewModelOf(::LoginVM)
    viewModelOf(::IndexVM)
}