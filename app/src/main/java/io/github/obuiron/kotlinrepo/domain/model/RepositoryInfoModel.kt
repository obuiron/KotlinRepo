package io.github.obuiron.kotlinrepo.domain.model

data class RepositoryInfoModel(
    val id: String,
    val fullName: String,
    val description: String,
    val starCount: Int,
    val forkCount: Int,
    val watcherCount: Int,
    val releaseCount: Int,
    val issueCount: Int,
    val pullRequestCount: Int,
    val image: String,
    val starred: Boolean,
)
