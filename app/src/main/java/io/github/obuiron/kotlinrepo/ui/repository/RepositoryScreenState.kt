package io.github.obuiron.kotlinrepo.ui.repository

import io.github.obuiron.kotlinrepo.domain.model.RepositoryInfoModel

sealed class RepositoryScreenState {
    object Empty : RepositoryScreenState()
    object Loading : RepositoryScreenState()
    data class Loaded(val repository: RepositoryInfoModel) : RepositoryScreenState()
}
