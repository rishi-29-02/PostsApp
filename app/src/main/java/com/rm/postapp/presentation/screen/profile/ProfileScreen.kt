package com.rm.postapp.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.postapp.R

@Composable
@Preview(showBackground = true)
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {

    val versionName by viewModel.versionName.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {

        IconButton(
            onClick = {
                onBackClick()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }


        Column(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.user_profile_icon_size)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.very_large_dp))
                    .background(color = Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("RM", fontSize = dimensionResource(R.dimen.user_profile_icon_size).value.sp)
            }

            Row(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_M))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextCount(
                    20, "Likes"
                )

                VerticalDivider(
                    Modifier
                        .padding(dimensionResource(R.dimen.padding_S))
                        .width(1.dp)
                        .height(40.dp)
                        .background(color = Color.DarkGray),
                )

                TextCount(
                    10, "Comments"
                )

                VerticalDivider(
                    Modifier
                        .padding(dimensionResource(R.dimen.padding_S))
                        .width(1.dp)
                        .height(40.dp)
                        .background(color = Color.DarkGray),
                )

                TextCount(
                    2, "Posts"
                )
            }

            Detail(
                imageVector = Icons.Default.Email,
                title = "Email",
                value = "myposts1235@yahoo.com"
            )

            Detail(
                imageVector = Icons.Default.Phone,
                title = "Phone",
                value = "+91-9876543219"
            )

            Detail(
                imageVector = Icons.Default.Web,
                title = "Website",
                value = "https/::www.myposts.com"
            )

            Detail(
                imageVector = Icons.Default.Home,
                title = "Address",
                value = "Main Road 12, Church Street \n Bangalore, India"
            )
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_M)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text("Version :$versionName")
        }
    }
}

@Composable
fun TextCount(
    count: Int,
    text: String
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            count.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(R.dimen.padding_M).value.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_S)))

        Text(text, fontSize = dimensionResource(R.dimen.padding_M).value.sp)
    }
}

@Composable
fun Detail(
    imageVector: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_M)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Email Icon"
        )

        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_M))
                .weight(1f)
        ) {
            Text(
                title,
            )

            Text(
                text = value,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}