package me.rerere.awara

import android.app.Application
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.ui.page.index.IndexVM
import me.rerere.awara.util.SerializationConverterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
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
            .addInterceptor {
                val response = it.proceed(it.request())
                println(response.code)
                println(response.headers)
                println(response.body)
                // TODO: 检查响应异常信息
                response
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

    }
}

val viewModelModule = module {
    viewModelOf(::IndexVM)
}