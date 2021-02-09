package io.github.obuiron.kotlinrepo.domain.auth

import io.github.obuiron.kotlinrepo.data.repository.AuthRepository

class AuthUseCase(val repository: AuthRepository) {

    fun isAuth() = repository.isAuth()

    suspend fun validate(response: String?, error: String?) = repository.validate(response, error)
}
