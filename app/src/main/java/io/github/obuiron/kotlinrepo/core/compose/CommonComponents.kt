package io.github.obuiron.kotlinrepo.core.compose

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import io.github.obuiron.kotlinrepo.R

@Composable
fun isTablet(): Boolean = AmbientContext.current.resources.getBoolean(R.bool.tablet)

@Composable
fun activity(): Activity {
    var context = AmbientContext.current
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    throw IllegalStateException("Cannot retrieve activity from AmbientContext")
}
