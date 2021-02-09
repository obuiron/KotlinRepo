package io.github.obuiron.kotlinrepo.ui.repository

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import io.github.obuiron.kotlinrepo.R
import io.github.obuiron.kotlinrepo.domain.model.RepositoryInfoModel
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun RepositoryScreen(
    repositoryId: String,
    viewModel: RepositoryViewModel = getViewModel()
) {
    LaunchedEffect(repositoryId) {
        launch { viewModel.load(repositoryId) }
    }
    when (val state = viewModel.state.collectAsState().value) {
        RepositoryScreenState.Empty -> EmptyView()
        RepositoryScreenState.Loading -> LoadingView()
        is RepositoryScreenState.Loaded -> RepositoryView(
            state.repository,
            viewModel.metrics.collectAsState()
        )
    }
}

@Composable
private fun RepositoryView(
    repository: RepositoryInfoModel,
    metricsState: State<List<RepositoryMetrics>>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            CoilImage(
                data = repository.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                contentScale = ContentScale.Crop,
                contentDescription = repository.fullName
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.5f))
            ) {
                Text(
                    text = repository.fullName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Card(
            modifier = Modifier.padding(bottom = 4.dp),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = repository.description,
                    style = MaterialTheme.typography.subtitle1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val starredColor = if (repository.starred) {
                        Color.Yellow
                    } else {
                        Color.Unspecified
                    }
                    Text(
                        text = "${stringResource(R.string.stars)}: ${repository.starCount}",
                        color = starredColor
                    )
                    Text(text = "${stringResource(R.string.forks)}: ${repository.forkCount}")
                    Text(text = "${stringResource(R.string.watchers)}: ${repository.watcherCount}")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${stringResource(R.string.issues)}: ${repository.issueCount}")
                    Text(text = "${stringResource(R.string.releases)}: ${repository.releaseCount}")
                    Text(text = "${stringResource(R.string.pull_requests)}: ${repository.pullRequestCount}")
                }
            }
        }

        metricsState.value.let { metrics ->
            LazyColumn {
                items(metrics) { metric -> RepositoryMetricItemView(metric) }
                if (metrics.isEmpty()) {
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
            }
        }
    }
}

@Composable
private fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.repository_select_a_repository))
    }
}

@Composable
private fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

