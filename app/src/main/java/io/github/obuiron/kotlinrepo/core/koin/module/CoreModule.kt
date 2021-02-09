package io.github.obuiron.kotlinrepo.core.koin.module

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import net.openid.appauth.AuthorizationService
import org.koin.dsl.module

val coreModule = module {
    single { provideEncryptedSharedPreferences(get()) }
    single { provideAuthorizationService(get()) }
}

private fun provideEncryptedSharedPreferences(context: Context): EncryptedSharedPreferences {
    val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    val encryptedSharedPrefs = EncryptedSharedPreferences.create(
        context,
        "secrets",
        key,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return encryptedSharedPrefs as EncryptedSharedPreferences
}

private fun provideAuthorizationService(context: Context) = AuthorizationService(context)
