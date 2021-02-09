package io.github.obuiron.kotlinrepo

import android.app.Application
import io.github.obuiron.kotlinrepo.core.koin.KoinInitializer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}
