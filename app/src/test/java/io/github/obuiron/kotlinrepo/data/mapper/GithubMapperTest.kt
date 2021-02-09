package io.github.obuiron.kotlinrepo.data.mapper

import io.github.obuiron.kotlinrepo.GetGithubRepositoryQuery
import io.github.obuiron.kotlinrepo.fragment.RepositoriesResult
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class GithubMapperTest {

    @Test
    fun `should map repository search to repository overview`() {
        // Given
        val asRepository = RepositoriesResult.AsRepository(
            id = "id",
            nameWithOwner = "owner/repository",
            description = "description",
            viewerHasStarred = true,
            stargazerCount = 22,
            forkCount = 42,
            watchers = RepositoriesResult.Watchers(totalCount = 62),
            openGraphImageUrl = "http://owner.github/repository/image"
        )
        val result = RepositoriesResult.Edge(
            cursor = "cursor",
            node = RepositoriesResult.Node(asRepository = asRepository)
        )

        // When
        val overview = result.toModel()

        // Then
        overview.cursor `should be equal to` result.cursor
        overview.id `should be equal to` asRepository.id
        overview.fullName `should be equal to` asRepository.nameWithOwner
        overview.description `should be equal to` asRepository.description
        overview.starCount `should be equal to` asRepository.stargazerCount
        overview.forkCount `should be equal to` asRepository.forkCount
        overview.watcherCount `should be equal to` asRepository.watchers.totalCount
        overview.image `should be equal to` asRepository.openGraphImageUrl
        overview.starred `should be equal to` asRepository.viewerHasStarred
    }

    @Test
    fun `should map repository query to repository info`() {
        // Given
        val asRepository = GetGithubRepositoryQuery.AsRepository(
            id = "id",
            nameWithOwner = "owner/repository",
            description = "description",
            viewerHasStarred = true,
            stargazerCount = 22,
            forkCount = 42,
            watchers = GetGithubRepositoryQuery.Watchers(totalCount = 62),
            releases = GetGithubRepositoryQuery.Releases(totalCount = 82),
            issues = GetGithubRepositoryQuery.Issues(totalCount = 102),
            pullRequests = GetGithubRepositoryQuery.PullRequests(totalCount = 122),
            openGraphImageUrl = "http://owner.github/repository/image"
        )
        val result = GetGithubRepositoryQuery.Node(asRepository = asRepository)

        // When
        val info = result.toModel()

        // Then
        info.id `should be equal to` asRepository.id
        info.fullName `should be equal to` asRepository.nameWithOwner
        info.description `should be equal to` asRepository.description
        info.starCount `should be equal to` asRepository.stargazerCount
        info.forkCount `should be equal to` asRepository.forkCount
        info.watcherCount `should be equal to` asRepository.watchers.totalCount
        info.releaseCount `should be equal to` asRepository.releases.totalCount
        info.issueCount `should be equal to` asRepository.issues.totalCount
        info.pullRequestCount `should be equal to` asRepository.pullRequests.totalCount
        info.image `should be equal to` asRepository.openGraphImageUrl
        info.starred `should be equal to` asRepository.viewerHasStarred
    }
}
