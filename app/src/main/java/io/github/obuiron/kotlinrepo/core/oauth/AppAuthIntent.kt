package io.github.obuiron.kotlinrepo.core.oauth

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import io.github.obuiron.kotlinrepo.BuildConfig
import io.github.obuiron.kotlinrepo.MainActivity
import net.openid.appauth.*

object AppAuthIntent {
    private const val EXTRA_OAUTH = "oauth"

    fun isAppAuthIntent(intent: Intent) = intent.getStringExtra(EXTRA_OAUTH).equals(EXTRA_OAUTH)

    fun getResponse(intent: Intent): String? =
        intent.getStringExtra(AuthorizationResponse.EXTRA_RESPONSE)

    fun getError(intent: Intent): String? =
        intent.getStringExtra(AuthorizationException.EXTRA_EXCEPTION)

    fun request(activity: Activity) {
        val service = AuthorizationService(activity)
        val config = AuthorizationServiceConfiguration(
            BuildConfig.OAUTH_AUTH_ENDPOINT.toUri(),
            BuildConfig.OAUTH_TOKEN_ENDPOINT.toUri()
        )

        val request = AuthorizationRequest.Builder(
            config,
            BuildConfig.OAUTH_CLIENT_ID,
            ResponseTypeValues.CODE,
            BuildConfig.OAUTH_REDIRECT.toUri()
        ).build()

        val customTabs = service.createCustomTabsIntentBuilder(request.toUri()).build()
        val redirect = Intent(activity, MainActivity::class.java)
            .apply { putExtra(EXTRA_OAUTH, EXTRA_OAUTH) }
            .toPendingIntent(activity)

        service.performAuthorizationRequest(request, redirect, redirect, customTabs)
        service.dispose()
    }

    private fun String.toUri(): Uri = Uri.parse(this)

    private fun Intent.toPendingIntent(context: Context): PendingIntent {
        return PendingIntent.getActivity(context, 0, this, 0)
    }
}
