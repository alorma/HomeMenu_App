package com.alorma.homemenu.screen

import androidx.compose.foundation.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.alorma.homemenu.R

@Composable
fun CreateDayScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.create_day)
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(asset = vectorResource(id = R.drawable.ic_back))
                    }
                }
            )
        }
    ) {
        Text(text = "Create")
    }
}