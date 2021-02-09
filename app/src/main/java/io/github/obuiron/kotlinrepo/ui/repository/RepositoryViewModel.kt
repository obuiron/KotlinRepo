package io.github.obuiron.kotlinrepo.ui.repository

import androidx.lifecycle.ViewModel
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics
import io.github.obuiron.kotlinrepo.domain.repository.RepositoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RepositoryViewModel(private val useCase: RepositoryUseCase) : ViewModel() {
    val state = MutableStateFlow<RepositoryScreenState>(RepositoryScreenState.Empty)
    val metrics = MutableStateFlow<List<RepositoryMetrics>>(emptyList())

    suspend fun load(id: String) {
        withContext(Dispatchers.IO) {
            state.emit(RepositoryScreenState.Loading)
            useCase.getRepository(id).let { repository ->
                state.emit(RepositoryScreenState.Loaded(repository))
                loadMetrics(repository.fullName)
            }
        }
    }

    private suspend fun loadMetrics(fullName: String) {
        useCase.getRepositoryAnnualMetrics(fullName).let { metrics ->
            this.metrics.emit(metrics)
        }
    }
}
