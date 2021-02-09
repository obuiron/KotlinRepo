package io.github.obuiron.kotlinrepo.ui.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Prepend
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import io.github.obuiron.kotlinrepo.domain.model.RepositoryOverviewModel
import io.github.obuiron.kotlinrepo.domain.repositories.RepositoriesUseCase

class RepositoriesPagingSource(private val useCase: RepositoriesUseCase) :
    PagingSource<String, RepositoryOverviewModel>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RepositoryOverviewModel> {
        return try {
            val result = when (params) {
                is Prepend -> useCase.getKotlinRepositoriesPrevious(params.loadSize, params.key)
                else -> useCase.getKotlinRepositoriesNext(params.loadSize, params.key)
            }
            Page(
                data = result.entries,
                prevKey = result.takeIf { it.hasPrevious }?.start,
                nextKey = result.takeIf { it.hasNext }?.end
            )
        } catch (exception: Exception) {
            Log.e("RepoPagingSource", "exceptiion", exception)
            Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, RepositoryOverviewModel>): String? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.cursor
        }
    }

}
