package me.rerere.awara

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import me.rerere.awara.di.networkModule
import me.rerere.awara.di.repoModule
import me.rerere.awara.di.userCaseModule
import me.rerere.awara.di.viewModelModule
import me.rerere.awara.ui.registerErrorHandler
import me.rerere.compose_setting.preference.initComposeSetting
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class App : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        registerErrorHandler()
        initComposeSetting()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                repoModule,
                viewModelModule,
                userCaseModule
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor {
                        // Refer: https://www.iwara.tv
                        val request = it.request()
                        val newRequest = request.newBuilder()
                            .addHeader("Referer", "https://www.iwara.tv")
                            .build()
                        it.proceed(newRequest)
                    }
                    .build()
            }
            .components {
                add(SvgDecoder.Factory())
            }
            .error(R.drawable.cancel)
            .build()
    }
}