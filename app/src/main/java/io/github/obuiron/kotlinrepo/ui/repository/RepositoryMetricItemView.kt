package io.github.obuiron.kotlinrepo.ui.repository

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.obuiron.kotlinrepo.R
import io.github.obuiron.kotlinrepo.domain.model.RepositoryMetrics

@Composable
fun RepositoryMetricItemView(metric: RepositoryMetrics) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "${metric.start} - ${metric.end}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${stringResource(R.string.issues)}: ${metric.issueCount}")
                Text(text = "${stringResource(R.string.pull_requests)}: ${metric.pullRequestCount}")
            }
        }
    }
}
