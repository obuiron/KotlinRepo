package io.github.obuiron.kotlinrepo.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.obuiron.kotlinrepo.R
import io.github.obuiron.kotlinrepo.core.compose.activity

@Composable
fun AuthScreen(viewModel: AuthViewModel, onAuthenticated: () -> Unit) {
    val activity = activity()

    val auth = viewModel.auth.collectAsState()
    if (auth.value) {
        onAuthenticated()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.onLogInWithGithub(activity) }) {
            Text(text = stringResource(R.string.auth_log_in_with_github))
        }
    }
}
