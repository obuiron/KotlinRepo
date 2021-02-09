package io.github.obuiron.kotlinrepo.core.koin.module

import io.github.obuiron.kotlinrepo.domain.auth.AuthUseCase
import io.github.obuiron.kotlinrepo.domain.repositories.RepositoriesUseCase
import io.github.obuiron.kotlinrepo.domain.repository.RepositoryUseCase
import org.koin.dsl.module

val domainModule = module {
    single { AuthUseCase(get()) }
    single { RepositoriesUseCase(get()) }
    single { RepositoryUseCase(get()) }
}
