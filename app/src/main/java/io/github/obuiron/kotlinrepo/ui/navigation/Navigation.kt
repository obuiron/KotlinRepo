package io.github.obuiron.kotlinrepo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import io.github.obuiron.kotlinrepo.ui.auth.AuthScreen
import io.github.obuiron.kotlinrepo.ui.auth.AuthViewModel
import io.github.obuiron.kotlinrepo.ui.repositories.RepositoriesScreen
import io.github.obuiron.kotlinrepo.ui.repository.RepositoryScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation(viewModel: AuthViewModel = getViewModel()) {
    val auth = viewModel.auth.collectAsState()
    LaunchedEffect(auth) {
        viewModel.checkAuth()
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.default(auth.value)
    ) {
        composable(route = Routes.AUTH) { AuthScreen(viewModel) { navController.navigate(Routes.REPO_LIST) } }
        composable(route = Routes.REPO_LIST) { RepositoriesScreen { navController.navigate(Routes.REPO_DETAIL + "/${it}") } }
        composable(route = Routes.REPO_DETAIL + "/{id}") { backStack ->
            RepositoryScreen(
                repositoryId = backStack.arguments?.get("id") as String
            )
        }
    }
}

object Routes {
    const val AUTH = "auth"
    const val REPO_LIST = "repo-list"
    const val REPO_DETAIL = "repo-detail"

    fun default(auth: Boolean): String {
        if (auth) {
            return REPO_LIST
        }
        return AUTH
    }
}
