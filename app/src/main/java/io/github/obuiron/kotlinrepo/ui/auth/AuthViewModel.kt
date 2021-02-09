package io.github.obuiron.kotlinrepo.ui.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.obuiron.kotlinrepo.core.oauth.AppAuthIntent
import io.github.obuiron.kotlinrepo.domain.auth.AuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val useCase: AuthUseCase) : ViewModel() {

    val auth = MutableStateFlow(false)

    suspend fun checkAuth() = withContext(Dispatchers.IO) {
        auth.emit(useCase.isAuth())
    }

    fun onLogInWithGithub(activity: Activity) = AppAuthIntent.request(activity)

    fun onIntent(intent: Intent) {
        if (AppAuthIntent.isAppAuthIntent(intent)) {
            val response = AppAuthIntent.getResponse(intent)
            val error = AppAuthIntent.getError(intent)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = useCase.validate(response, error)
                    auth.emit(result)
                    Log.d("AuthViewModel", "success")
                } catch (exception: Exception) {
                    Log.e("AuthViewModel", "error", exception)
                }
            }
        }
    }
}
