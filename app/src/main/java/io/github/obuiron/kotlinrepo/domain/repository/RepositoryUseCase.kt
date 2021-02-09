package io.github.obuiron.kotlinrepo.domain.repository

import io.github.obuiron.kotlinrepo.data.repository.GithubRepository
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics
import kotlinx.coroutines.*
import kotlinx.datetime.*

class RepositoryUseCase(private val repository: GithubRepository) {

    suspend fun getRepository(id: String) = repository.getGithubRepository(id)

    suspend fun getRepositoryAnnualMetrics(
        fullName: String,
        today: LocalDate = now()
    ): List<RepositoryMetrics> {
        val metrics = mutableListOf<Deferred<RepositoryMetrics>>()
        withContext(Dispatchers.IO) {
            var end = today
            val yesteryear = today.minus(1, DateTimeUnit.YEAR)
            while (end > yesteryear) {
                val start = end.minus(1, DateTimeUnit.WEEK)
                metrics.add(
                    getIntervalMetricAsync(this, fullName, start, end)
                )
                end = start
            }
        }
        return metrics.awaitAll()
    }

    private suspend fun getIntervalMetricAsync(
        scope: CoroutineScope,
        fullName: String,
        start: LocalDate,
        end: LocalDate
    ): Deferred<RepositoryMetrics> = scope.async {
        repository.getGithubRepositoryMetricsForInterval(
            fullName = fullName,
            start = start,
            end = end
        )
    }

    private fun now(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
}
