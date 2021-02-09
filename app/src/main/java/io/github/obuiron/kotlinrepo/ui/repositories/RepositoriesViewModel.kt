package io.github.obuiron.kotlinrepo.ui.repositories

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.obuiron.kotlinrepo.domain.repositories.RepositoriesUseCase

class RepositoriesViewModel(private val useCase: RepositoriesUseCase) : ViewModel() {

    val repositories = Pager(PagingConfig(pageSize = 20)) { RepositoriesPagingSource(useCase) }.flow
}
