package io.github.obuiron.kotlinrepo.core.koin

import android.content.Context
import io.github.obuiron.kotlinrepo.core.koin.module.coreModule
import io.github.obuiron.kotlinrepo.core.koin.module.dataModule
import io.github.obuiron.kotlinrepo.core.koin.module.domainModule
import io.github.obuiron.kotlinrepo.core.koin.module.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinInitializer {
    val modules = listOf(
        coreModule,
        uiModule,
        domainModule,
        dataModule,
    )

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}
