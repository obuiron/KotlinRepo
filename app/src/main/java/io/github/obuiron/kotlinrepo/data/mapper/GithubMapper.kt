package io.github.obuiron.kotlinrepo.data.mapper

import io.github.obuiron.kotlinrepo.GetGithubRepositoryQuery
import io.github.obuiron.kotlinrepo.domain.model.RepositoryInfoModel
import io.github.obuiron.kotlinrepo.domain.model.RepositoryOverviewModel
import io.github.obuiron.kotlinrepo.fragment.RepositoriesResult

fun RepositoriesResult.Edge.toModel(): RepositoryOverviewModel {
    val repo = this.node?.asRepository!!
    return RepositoryOverviewModel(
        cursor = this.cursor,
        id = repo.id,
        fullName = repo.nameWithOwner,
        description = repo.description ?: "",
        starCount = repo.stargazerCount,
        forkCount = repo.forkCount,
        watcherCount = repo.watchers.totalCount,
        image = repo.openGraphImageUrl,
        starred = repo.viewerHasStarred
    )
}

fun GetGithubRepositoryQuery.Node.toModel(): RepositoryInfoModel {
    val repo = this.asRepository!!
    return RepositoryInfoModel(
        id = repo.id,
        fullName = repo.nameWithOwner,
        description = repo.description ?: "",
        starCount = repo.stargazerCount,
        forkCount = repo.forkCount,
        watcherCount = repo.watchers.totalCount,
        releaseCount = repo.releases.totalCount,
        issueCount = repo.issues.totalCount,
        pullRequestCount = repo.pullRequests.totalCount,
        image = repo.openGraphImageUrl,
        starred = repo.viewerHasStarred
    )
}
