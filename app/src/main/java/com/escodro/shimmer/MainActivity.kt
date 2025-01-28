package com.escodro.shimmer

import android.R.drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.escodro.shimmer.ui.modifier.ShimmerProvider
import com.escodro.shimmer.ui.modifier.shimmerable
import com.escodro.shimmer.ui.theme.ExampleTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleTheme {
                Content()
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val mainState by mainViewModel.state.collectAsStateWithLifecycle(MainState.Loading)

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(mainState) { state ->
                when (state) {
                    MainState.Loading -> {
                        ShimmerProvider {
                            ItemCard(item = fakeData)
                        }
                    }

                    is MainState.Success -> {
                        ItemCard(item = state.listItem)
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemCard(
    item: ItemData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .shimmerable(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .shimmerable()
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.shimmerable()
                )
            }
        }
    }
}

private val fakeData = ItemData(
    imageResId = drawable.ic_dialog_info,
    title = LoremIpsum(4).values.toList().first(),
    description = LoremIpsum(8).values.toList().first(),
)

@Composable
private fun LoadingComposable() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(48.dp)
    )
}

@PreviewLightDark
@Composable
fun ItemDataPreview() {
    ExampleTheme {
        ItemCard(item = fakeData)
    }
}

@PreviewLightDark
@Composable
fun ItemDataLoadingPreview() {
    ExampleTheme {
        ShimmerProvider(isLoading = true) {
            ItemCard(item = fakeData)
        }
    }
}
