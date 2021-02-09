package io.github.obuiron.kotlinrepo.domain.model

data class RepositoryOverviewModel(
    val cursor: String,
    val id: String,
    val fullName: String,
    val description: String,
    val starCount: Int,
    val forkCount: Int,
    val watcherCount: Int,
    val image: String,
    val starred: Boolean,
)
