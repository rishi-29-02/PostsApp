package com.rm.postapp.presentation.screen.postdescription

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rm.postapp.presentation.components.IconTextButton
import com.rm.postapp.presentation.components.UserProfileIcon
import com.rm.postapp.presentation.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDescriptionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PostDescriptionViewModel = hiltViewModel(),
    commentViewModel : PostCommentViewModel = hiltViewModel()

) {
    /*
    LaunchedEffect(postId) {
        viewModel.fetchPost(postId)
        Not Recommended
        Reason:
UI is triggering business logic.
If the composable recomposes unexpectedly, you need to ensure the effect doesn't rerun incorrectly.
The ViewModel lifecycle isn't fully in control.
    }*/
    val state by viewModel.postDescriptionState.collectAsStateWithLifecycle()
    val comment by commentViewModel.comments.collectAsStateWithLifecycle()
    var showComments by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(24) }

    Scaffold(
        topBar = {
            PostDescriptionHeader(
                modifier = modifier, postID = viewModel.postId,
                navController = navController
            )
        }
    ) { padding ->
        when (state) {
            UiState.Loading -> {
                CircularProgressIndicator(
                )
            }

            is UiState.Success<*> -> {
                val postDescription = (state as UiState.Success).data
                Card(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        AuthorSection(
                            userId = postDescription.userId
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = postDescription.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = postDescription.body,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 28.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(12.dp))

                        PostFooter(
                            onShareContentClicked = {
                                viewModel.sharePost()
                            },
                            isLiked = isLiked,
                            likeCount = likeCount,
                            commentCount = comment.size,
                            onLikeClick = {
                                likeCount += if (isLiked) -1 else 1
                                isLiked = !isLiked
                            },
                            onCommentClick = {
                                showComments = true
                            },
                        )
                    }
                }

            }

            is UiState.Error -> {
                Text(
                    text = (state as UiState.Error).message
                )
            }
        }


    }
    if (showComments) {
        ModalBottomSheet(
            onDismissRequest = {
                showComments = false
            }
        ) {
            CommentBottomSheet(
                comments = comment,
                onDismiss = {
                    showComments = false
                }
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDescriptionHeader(
    modifier: Modifier = Modifier,
    postID: Int,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text("Post #$postID")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },

        )
}

@Composable
fun PostFooter(
    onShareContentClicked: () -> Unit,
    isLiked: Boolean,
    likeCount: Int,
    commentCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconTextButton(
            icon = if (isLiked)
                Icons.Filled.Favorite
            else
                Icons.Outlined.FavoriteBorder,
            text = likeCount.toString(),
            tint = if (isLiked) Color.Red else LocalContentColor.current,
            onClick = onLikeClick
        )


        Spacer(Modifier.width(24.dp))

        IconTextButton(
            icon = Icons.AutoMirrored.Outlined.Chat,
            text = commentCount.toString(),
            onClick = onCommentClick
        )

        Spacer(modifier = Modifier.weight(1f))

        IconTextButton(
            icon = Icons.Outlined.Share,
            onClick = onShareContentClicked
        )
    }
}

@Composable
fun AuthorSection(
    userId: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        UserProfileIcon(
            modifier = Modifier,
            user = "U$userId",
            textColor = Color.White,
            backgroundColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = "User $userId",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Author id · $userId",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}