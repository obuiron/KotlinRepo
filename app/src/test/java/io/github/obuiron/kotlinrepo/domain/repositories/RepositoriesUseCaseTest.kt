package io.github.obuiron.kotlinrepo.domain.repositories

import io.github.obuiron.kotlinrepo.data.repository.GithubRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoriesUseCaseTest {

    val repository: GithubRepository = mockk {
        coEvery { getKotlinRepositoriesNext(any(), any()) } returns mockk()
        coEvery { getKotlinRepositoriesPrevious(any(), any()) } returns mockk()
    }

    val useCase = RepositoriesUseCase(repository)

    @Test
    fun `should forward get next repositories call to repository with same parameters`() {
        // Given
        val first = 20
        val after = "cursor"

        // When
        runBlocking {
            useCase.getKotlinRepositoriesNext(first = first, after = after)
        }

        // Then
        coVerify(exactly = 1) {
            repository.getKotlinRepositoriesNext(first = eq(first), after = eq(after))
        }
    }

    @Test
    fun `should forward get previous repositories call to repository with same parameter`() {
        // Given
        val last = 20
        val before = "cursor"

        // When
        runBlocking {
            useCase.getKotlinRepositoriesPrevious(last = last, before = before)
        }

        // Then
        coVerify(exactly = 1) {
            repository.getKotlinRepositoriesPrevious(last = eq(last), before = eq(before))
        }
    }
}
