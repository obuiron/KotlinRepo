package io.github.obuiron.kotlinrepo.domain.model

data class Page<T>(
    val start: String?,
    val end: String?,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val entries: List<T>,
)
