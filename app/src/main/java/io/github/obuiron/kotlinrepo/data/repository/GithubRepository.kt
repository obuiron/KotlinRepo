package io.github.obuiron.kotlinrepo.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import io.github.obuiron.kotlinrepo.GetGithubRepositoryQuery
import io.github.obuiron.kotlinrepo.KotlinGithubRepositoriesNextQuery
import io.github.obuiron.kotlinrepo.KotlinGithubRepositoriesPreviousQuery
import io.github.obuiron.kotlinrepo.RepositoryIssueCountQuery
import io.github.obuiron.kotlinrepo.data.mapper.toModel
import io.github.obuiron.kotlinrepo.domain.model.Page
import io.github.obuiron.kotlinrepo.domain.model.RepositoryInfoModel
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics
import io.github.obuiron.kotlinrepo.domain.model.RepositoryOverviewModel
import io.github.obuiron.kotlinrepo.fragment.RepositoriesResult
import kotlinx.datetime.LocalDate

class GithubRepository(private val client: ApolloClient) {

    suspend fun getKotlinRepositoriesPrevious(
        last: Int,
        before: String?
    ): Page<RepositoryOverviewModel> {
        return client.query(
            KotlinGithubRepositoriesPreviousQuery(last.toInput(), before.toInput())
        ).await().let { response ->
            if (response.hasErrors()) {
                throw IllegalStateException("Invalid query KotlinGithubRepositoriesPreviousQuery")
            }
            response.data!!.search.fragments.repositoriesResult.toPage()
        }
    }

    suspend fun getKotlinRepositoriesNext(
        first: Int,
        after: String?
    ): Page<RepositoryOverviewModel> {
        return client.query(
            KotlinGithubRepositoriesNextQuery(first.toInput(), after.toInput())
        ).await().let { response ->
            if (response.hasErrors()) {
                throw IllegalStateException("Invalid query KotlinGithubRepositoriesNextQuery")
            }
            response.data!!.search.fragments.repositoriesResult.toPage()
        }
    }

    private fun RepositoriesResult.toPage(): Page<RepositoryOverviewModel> = Page(
        start = pageInfo.startCursor,
        end = pageInfo.endCursor,
        hasPrevious = pageInfo.hasPreviousPage,
        hasNext = pageInfo.hasNextPage,
        entries = edges!!.mapNotNull { node -> node?.toModel() }
    )

    suspend fun getGithubRepository(id: String): RepositoryInfoModel {
        return client.query(GetGithubRepositoryQuery(id)).await().let { response ->
            if (response.hasErrors()) {
                throw IllegalStateException("Invalid query GetGithubRepositoryQuery")
            }
            response.data!!.node!!.toModel()
        }
    }

    suspend fun getGithubRepositoryMetricsForInterval(
        fullName: String,
        start: LocalDate,
        end: LocalDate
    ): RepositoryMetrics {
        val issueCount =
            client.query(RepositoryIssueCountQuery("repo:$fullName is:issue created:$start..$end"))
                .await()
        val pullRequestCount =
            client.query(RepositoryIssueCountQuery("repo:$fullName is:pr created:$start..$end"))
                .await()
        if (issueCount.hasErrors() || pullRequestCount.hasErrors()) {
            throw IllegalStateException("Invalid query RepositoryIssueCountQuery")
        }
        return RepositoryMetrics(
            start = start,
            end = end,
            issueCount = issueCount.data!!.search.issueCount,
            pullRequestCount = pullRequestCount.data!!.search.issueCount
        )
    }

    private fun <T : Any> T?.toInput(): Input<T> = Input.fromNullable(this)
}

