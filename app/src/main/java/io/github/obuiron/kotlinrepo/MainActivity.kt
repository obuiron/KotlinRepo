package io.github.obuiron.kotlinrepo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import io.github.obuiron.kotlinrepo.ui.auth.AuthViewModel
import io.github.obuiron.kotlinrepo.ui.navigation.Navigation
import io.github.obuiron.kotlinrepo.ui.theme.SampleAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppSample() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onIntent(intent)
    }

    @Composable
    fun AppSample() {
        SampleAppTheme {
            Scaffold(topBar = {
                TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
            }) {
                Navigation()
            }
        }
    }
}
