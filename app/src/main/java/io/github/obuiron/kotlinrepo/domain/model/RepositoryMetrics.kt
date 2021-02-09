package io.github.obuiron.kotlinrepo.domain.model

import kotlinx.datetime.LocalDate

data class RepositoryMetrics(
    val start: LocalDate,
    val end: LocalDate,
    val issueCount: Int,
    val pullRequestCount: Int
)
