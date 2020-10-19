package com.alorma.homemenu.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.R
import com.alorma.homemenu.ui.HomeMenuTheme

@Composable
fun shapeBodyContent(
    shape: Shape? = null,
    content: @Composable () -> Unit,
) {
    val bodyShape: Shape = shape ?: MaterialTheme.shapes.large.copy(
        topLeft = CornerSize(16.dp),
        topRight = CornerSize(0.dp),
        bottomLeft = CornerSize(0.dp),
        bottomRight = CornerSize(0.dp)
    )

    Surface(
        elevation = 4.dp,
        modifier = Modifier
            .clip(shape = bodyShape)
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
fun preview() {
    HomeMenuTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
            topBar = {
                val title = stringResource(id = R.string.app_name)
                TopAppBar(
                    elevation = 0.dp,
                    title = { Text(text = title) }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Add") },
                    shape = RoundedCornerShape(percent = 50),
                    onClick = { },
                )
            },
            floatingActionButtonPosition = FabPosition.End,
        ) {
            shapeBodyContent {
                Text("No items")
            }
        }
    }
}