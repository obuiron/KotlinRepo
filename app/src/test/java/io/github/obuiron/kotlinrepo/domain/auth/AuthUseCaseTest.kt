package io.github.obuiron.kotlinrepo.domain.auth

import io.github.obuiron.kotlinrepo.data.repository.AuthRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AuthUseCaseTest {

    val repository: AuthRepository = mockk {
        every { isAuth() } returns true
        coEvery { validate(any(), any()) } returns true
    }

    val useCase = AuthUseCase(repository)

    @Test
    fun `should forward isAuth call to repository`() {
        // When
        useCase.isAuth()

        // Then
        verify(exactly = 1) { repository.isAuth() }
    }

    @Test
    fun `should forward validate call to repository with same parameters`() {
        // Given
        val response = "response"
        val error = "error"

        // When
        runBlocking {
            useCase.validate(response = response, error = error)
        }

        // Then
        coVerify(exactly = 1) { repository.validate(response = eq(response), error = eq(error)) }
    }
}
