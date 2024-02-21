package com.example.cvscodechallenge

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cvscodechallenge.domain.model.FlickrSearchUiState
import com.example.cvscodechallenge.domain.model.Photo
import com.example.cvscodechallenge.domain.model.Photos
import com.example.cvscodechallenge.domain.util.formatDate
import com.example.cvscodechallenge.ui.theme.CVSCodeChallengeTheme
import com.example.cvscodechallenge.ui.viewmodel.FlickrSearchViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CVSCodeChallengeTheme {
                val navController = rememberNavController()
                val viewModel: FlickrSearchViewModel = koinViewModel()
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.route,
                    route = "route"
                ) {
                    composable(route = Screens.Home.route) {
                        MainActivityContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(route = Screens.ImageDetails.route) { ImageDetails(viewModel = viewModel) }
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FlickrSearchViewModel
) {
    val flickrSearchUiState = viewModel.flickrSearchImages.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FlickrSearchBar(
            queryText = viewModel.searchQuery,
            onSearch = { },
            onQueryChanged = { query -> viewModel.onSearchQueryChange(query) },
            modifier = modifier.weight(0.1f)
        )

        // When statement to set up the UI based on the UiState provided by ViewModel
        when (flickrSearchUiState.value) {
            // Success - loads a small title and grid of photos returned from service
            is FlickrSearchUiState.Success -> {
                Row(
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = (flickrSearchUiState.value as FlickrSearchUiState.Success).data.title.orEmpty(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                PhotoGrid(
                    photos = (flickrSearchUiState.value as FlickrSearchUiState.Success).data,
                    onImageClick = { photo ->
                        viewModel.onClickImage(photo)
                        navController.navigate(Screens.ImageDetails.route)
                    },
                    modifier = modifier.weight(0.9f)
                )
            }

            // Error - if there's an error or exception in the search, the message will display
            is FlickrSearchUiState.Error -> {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = modifier
                        .weight(0.9f)
                        .padding(top = 32.dp)
                ) {
                    Text(
                        text = (flickrSearchUiState.value as FlickrSearchUiState.Error).errorMessage,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            // When loading, progress indicator is shown
            is FlickrSearchUiState.Loading -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.weight(0.9f)
                ) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .width(72.dp)
                            .height(72.dp)
                    )
                }
            }

            // If the state is FlickrSearchUiState.Empty, show a message explaining how to use the app
            else -> {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = modifier
                        .weight(0.9f)
                        .padding(top = 32.dp)
                ) {
                    Text(
                        text = "Search For Images!",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}

// Search Bar content
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickrSearchBar(
    queryText: String,
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = queryText,
        onQueryChange = { query -> onQueryChanged(query) },
        onSearch = { query -> onSearch(query) },
        active = true,
        onActiveChange = { },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = "Search"
            )
        },
        modifier = modifier.fillMaxWidth(),
        content = {}
    )
}

// Image details for selected image
@Composable
fun ImageDetails(
    modifier: Modifier = Modifier,
    viewModel: FlickrSearchViewModel
) {
    viewModel.selectedImage?.let { photo ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = modifier.wrapContentSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        text = photo.title.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = Html.fromHtml(
                            photo.description.orEmpty(),
                            Html.FROM_HTML_MODE_LEGACY
                        )
                            .toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = photo.author.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = "Published " + photo.published.formatDate(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier.padding(vertical = 2.dp)
                    )
                }
            }
            Column(modifier = modifier.padding(8.dp)) {
                AsyncImage(
                    ImageRequest.Builder(LocalContext.current)
                        .data(photo.media?.m)
                        .crossfade(true)
                        .build(),
                    contentDescription = photo.title,
                    contentScale = ContentScale.Fit,
                    modifier = modifier.fillMaxSize(),
                    alignment = Alignment.TopCenter,
                )
            }
        }
    }
}

// Grid of photos returned from search
@Composable
fun PhotoGrid(
    photos: Photos,
    onImageClick: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(photos.items) { photo ->
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(photo.media?.m)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onImageClick(photo)
                    }
            )
        }
    }
}

// sealed class for navigation
sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object ImageDetails : Screens("image_details")
}