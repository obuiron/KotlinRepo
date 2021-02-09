package io.github.obuiron.kotlinrepo.domain.repository

import io.github.obuiron.kotlinrepo.data.repository.GithubRepository
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.toLocalDate
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class RepositoryUseCaseTest {

    val repository: GithubRepository = mockk {
        coEvery { getGithubRepository(any()) } returns mockk()
        coEvery {
            getGithubRepositoryMetricsForInterval(any(), any(), any())
        } answers {
            RepositoryMetrics(
                start = this.secondArg(),
                end = this.thirdArg(),
                issueCount = 0,
                pullRequestCount = 0
            )
        }
    }

    val useCase = RepositoryUseCase(repository)

    @Test
    fun `should forward get repository call to repository with same id`() {
        // Given
        val repositoryId = "id"

        // When
        runBlocking {
            useCase.getRepository(repositoryId)
        }

        // Then
        coVerify(exactly = 1) { repository.getGithubRepository(eq(repositoryId)) }
    }

    @Test
    fun `should retrieve repository metrics for the passed year`() {
        // Given
        val fullName = "owner/repository"
        val date = "2021-02-08".toLocalDate()

        // When
        val metrics = mutableListOf<RepositoryMetrics>()
        runBlocking {
            metrics.addAll(useCase.getRepositoryAnnualMetrics(fullName, date))
        }

        // Then
        coVerify(exactly = 53) {
            repository.getGithubRepositoryMetricsForInterval(
                fullName = eq(fullName),
                start = any(),
                end = any()
            )
        }

        metrics.size `should be equal to` 53

        metrics.first().start.toString() `should be equal to` "2021-02-01"
        metrics.first().end.toString() `should be equal to` "2021-02-08"

        metrics.last().start.toString() `should be equal to` "2020-02-03"
        metrics.last().end.toString() `should be equal to` "2020-02-10"
    }
}
