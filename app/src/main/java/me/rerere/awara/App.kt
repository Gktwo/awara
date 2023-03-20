package me.rerere.awara

import android.app.Application
import me.rerere.awara.di.networkModule
import me.rerere.awara.di.repoModule
import me.rerere.awara.di.viewModelModule
import me.rerere.compose_setting.preference.initComposeSetting
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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




