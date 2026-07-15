package com.rm.postapp.presentation.screen.postHome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rm.postapp.R
import com.rm.postapp.domain.models.Post
import com.rm.postapp.presentation.components.UserProfileIcon
import com.rm.postapp.presentation.utils.UiState

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit,
    onProfileIconClick: () -> Unit
) {
    val state by viewModel.postState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val errorMessage = state.errorMessage
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackBarHostState.showSnackbar(errorMessage)
            viewModel.dismissError()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    contentColor = Color.Black,
                    containerColor = Color.White,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_M))
                )
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.onSearchTextQueryChange("")
                keyboardController?.hide()
                viewModel.refreshPosts()
            }
        ) {
            Column {
                PostHeader(
                    Modifier,
                    state.searchQuery,
                    viewModel::onSearchTextQueryChange,
                    onImeAction = {
                        keyboardController?.hide()
                    },
                    onProfileIconClick,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_M)),
                    contentAlignment = Alignment.Center
                ) {
                    when (val postState = state.postState) {
                        UiState.Loading -> {
                            ShowLottie(
                                Modifier
                                    .align(Alignment.Center)
                                    .verticalScroll(rememberScrollState()),
                                "loading.json",
                                100
                            )
                        }

                        is UiState.Success<List<Post>> -> {
                            val posts = postState.data

                            if (posts.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    ShowLottie(
                                        lottieName = "empty.json",
                                        size = 150
                                    )

                                    Spacer(Modifier.width(dimensionResource(R.dimen.spacer_width)))

                                    Text(
                                        text = stringResource(R.string.no_posts)
                                    )
                                }

                                keyboardController?.hide()
                            } else {
                                LazyColumn(
                                    modifier = Modifier.align(Alignment.TopCenter)
                                ) {
                                    items(posts) { post ->
                                        Card(
                                            modifier =
                                                Modifier
                                                    .fillMaxSize()
                                                    .padding(vertical = dimensionResource(R.dimen.padding_S))
                                                    .clickable {
                                                        onPostClick(post.id)
                                                    },
                                            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_S))
                                        ) {
                                            PostContent(
                                                modifier = Modifier,
                                                title = post.title,
                                                content = post.body,
                                                userID = post.userId,
                                                postID = post.id
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is UiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    ShowLottie(
                                        lottieName = "empty.json",
                                        size = 150
                                    )

                                    Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer_width)))

                                    Text(
                                        text = postState.message
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchTextQueryChange: (String) -> Unit,
    onImeAction: () -> Unit,
    onProfileIconClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_S))
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.PostAdd,
                contentDescription = "Post Icons"
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_S)))

            Text(
                text = "Posts",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1F))

            IconButton(
                onClick = {
                    onProfileIconClick()
                }
            ) {
                UserProfileIcon(modifier,"RM",Color.DarkGray,Color.LightGray)
            }
        }

        PostSearch(
            modifier.padding(top = dimensionResource(R.dimen.padding_XS)),
            searchQuery,
            onSearchTextQueryChange,
            onImeAction,
        )
    }
}


@Composable
fun PostSearch(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchTextQueryChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = searchQuery,
        onValueChange = { onSearchTextQueryChange(it) },
        placeholder = { Text("Search Posts") },
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeAction()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Email Icon"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchTextQueryChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            }
        }
    )
}

@Composable
fun ShowLottie(modifier: Modifier = Modifier, lottieName: String, size: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(lottieName)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition, { progress }, modifier.size(size.dp)
    )
}