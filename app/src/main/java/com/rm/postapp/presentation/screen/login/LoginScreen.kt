package com.rm.postapp.presentation.screen.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rm.postapp.R
import com.rm.postapp.presentation.utils.CustomerSpacer

@Composable
fun LoginScreen(
    onSignClicked: () -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAuthTyp by remember { mutableStateOf(AuthType.SignIn) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_M)),
            elevation = CardDefaults.cardElevation(
                dimensionResource(R.dimen.padding_XS)
            ),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_M)),
            ) {

                AuthSegmentText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    selectedAuthTyp
                ) {
                    selectedAuthTyp = it
                }
                
                TextField(
                    modifier = Modifier.padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Enter email or username"
                        )
                    },
                    value = email,
                    onValueChange = {
                        email = it
                    }
                )

                CustomerSpacer()

                TextField(
                    modifier = Modifier.padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Enter password"
                        )
                    },
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                )

                if (selectedAuthTyp == AuthType.SignUp) {
                    CustomerSpacer()

                    TextField(
                        modifier = Modifier.padding(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                "Enter confirm password"
                            )
                        },
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) {
                                        Icons.Default.Visibility
                                    } else {
                                        Icons.Default.VisibilityOff
                                    },
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(
                            horizontal =
                                dimensionResource(R.dimen.padding_M)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                    ),
                    onClick = {
                        onSignClicked()
                    }
                ) {
                    Text(
                        if (selectedAuthTyp== AuthType.SignIn) {
                            "Sign In"
                        } else {
                            " Sign Up"
                        },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensionResource(R.dimen.padding_M).value.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AuthSegmentText(
    modifier: Modifier = Modifier,
    selectedAuthTyp: AuthType,
    onTabSelected: (AuthType) -> Unit
) {

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(30.dp),
                color = Color.LightGray
            )
            .padding(4.dp)
        ,
    ) {
        val tabWidth = maxWidth / 2

        val animateOffset by animateDpAsState(
            targetValue = if (selectedAuthTyp == AuthType.SignIn) {
                0.dp
            } else {
                tabWidth
            }
        )

        Box(
            modifier = Modifier
                .offset(
                    x = animateOffset
                )
                .width(tabWidth - 4.dp)
                .clip(
                    shape = RoundedCornerShape(30.dp)
                )
                .fillMaxHeight()
                .background(Color.Red)
            ,
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            SegmentText(
                modifier = Modifier.weight(1f),
                AuthType.SignIn,
                selectedAuthTyp == AuthType.SignIn
            ) {
                onTabSelected(AuthType.SignIn)
            }

            SegmentText(
                modifier = Modifier.weight(1f),
                AuthType.SignUp,
                selectedAuthTyp == AuthType.SignUp
            ) {
                onTabSelected(AuthType.SignUp)
            }
        }
    }
}

@Composable
fun SegmentText(
    modifier: Modifier,
    authType: AuthType,
    selected: Boolean,
    onClick: () -> Unit
) {

    val color by animateColorAsState(
        targetValue = if (selected) {
            Color.White
        } else {
            Color.Red
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                onClick()
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = authType.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(R.dimen.padding_M).value.sp,
            color = color
        )
    }
}

enum class AuthType {
    SignIn,
    SignUp
}