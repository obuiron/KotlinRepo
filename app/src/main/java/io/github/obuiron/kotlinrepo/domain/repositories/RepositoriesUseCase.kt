package io.github.obuiron.kotlinrepo.domain.repositories

import io.github.obuiron.kotlinrepo.data.repository.GithubRepository
import io.github.obuiron.kotlinrepo.domain.model.Page
import io.github.obuiron.kotlinrepo.domain.model.RepositoryOverviewModel

class RepositoriesUseCase(private val repository: GithubRepository) {

    suspend fun getKotlinRepositoriesPrevious(
        last: Int,
        before: String? = null
    ): Page<RepositoryOverviewModel> = repository.getKotlinRepositoriesPrevious(last, before)

    suspend fun getKotlinRepositoriesNext(
        first: Int,
        after: String? = null
    ): Page<RepositoryOverviewModel> = repository.getKotlinRepositoriesNext(first, after)
}
