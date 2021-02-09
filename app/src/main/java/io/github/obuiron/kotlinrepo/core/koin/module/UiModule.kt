package io.github.obuiron.kotlinrepo.core.koin.module

import io.github.obuiron.kotlinrepo.ui.auth.AuthViewModel
import io.github.obuiron.kotlinrepo.ui.repositories.RepositoriesViewModel
import io.github.obuiron.kotlinrepo.ui.repository.RepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { RepositoriesViewModel(get()) }
    viewModel { RepositoryViewModel(get()) }
}
