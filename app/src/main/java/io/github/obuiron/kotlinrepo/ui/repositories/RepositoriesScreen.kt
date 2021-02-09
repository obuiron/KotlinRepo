package io.github.obuiron.kotlinrepo.ui.repositories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.androidx.compose.getViewModel

@Composable
fun RepositoriesScreen(
    viewModel: RepositoriesViewModel = getViewModel(),
    onRepositorySelected: (repositoryId: String) -> Unit
) {
    val repositories = viewModel.repositories.collectAsLazyPagingItems()
    LazyColumn {
        items(repositories) { repository ->
            repository?.let { repo ->
                RepositoryItemView(
                    repository = repo,
                    onRepoSelected = onRepositorySelected
                )
            }
        }
        repositories.loadState.let { state ->
            when {
                state.refresh is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                state.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}
