package com.alorma.homemenu.screen

import androidx.compose.foundation.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alorma.homemenu.R

@Composable
fun CreateDayScreen() {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.create_day)
            TopAppBar(
                title = { Text(text = title) },
            )
        }
    ) {
        Text(text = "Create")
    }
}