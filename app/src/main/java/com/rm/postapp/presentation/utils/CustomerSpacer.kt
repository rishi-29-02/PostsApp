package com.rm.postapp.presentation.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rm.postapp.R

@Composable
fun CustomerSpacer() {
    Spacer(Modifier.width(dimensionResource(R.dimen.spacer_width)))
}