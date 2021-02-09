package io.github.obuiron.kotlinrepo.data.repository

import android.content.Context
import io.github.obuiron.kotlinrepo.BuildConfig
import io.github.obuiron.kotlinrepo.data.local.EncryptedSharedPrefsDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepository(
    private val context: Context,
    private val prefs: EncryptedSharedPrefsDataSource
) {
    fun token() = prefs.token

    fun isAuth() = token().isNotEmpty()

    fun logout() = prefs.clear()

    suspend fun validate(response: String?, error: String?): Boolean =
        suspendCancellableCoroutine { continuation ->
            val service = AuthorizationService(context)

            val authResponse = response?.let { json -> AuthorizationResponse.jsonDeserialize(json) }
            val authError = error?.let { json -> AuthorizationException.fromJson(json) }

            when {
                authResponse != null -> {
                    val params = mapOf("client_secret" to BuildConfig.OAUTH_SECRET)
                    val tokenRequest = authResponse.createTokenExchangeRequest(params)
                    service.performTokenRequest(tokenRequest) { token, exception ->
                        if (token?.accessToken != null) {
                            saveToken(token.accessToken!!)
                            continuation.resume(true)
                        } else {
                            continuation.resumeWithException(
                                exception ?: IllegalStateException("Empty token")
                            )
                        }
                    }
                }
                authError != null -> continuation.resumeWithException(authError)
                else -> continuation.resumeWithException(IllegalStateException())
            }

            continuation.invokeOnCancellation { service.dispose() }
        }

    private fun saveToken(token: String) {
        prefs.token = token
    }
}
