package io.github.obuiron.kotlinrepo.ui.repositories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import io.github.obuiron.kotlinrepo.R
import io.github.obuiron.kotlinrepo.domain.model.RepositoryOverviewModel

@Composable
fun RepositoryItemView(
    repository: RepositoryOverviewModel,
    onRepoSelected: (id: String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
            .clickable { onRepoSelected(repository.id) },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(modifier = Modifier.preferredSize(50.dp)) {
                CoilImage(
                    data = repository.image,
                    contentDescription = repository.fullName
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = repository.fullName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = repository.description,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.subtitle1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
            }
        }
    }
}
