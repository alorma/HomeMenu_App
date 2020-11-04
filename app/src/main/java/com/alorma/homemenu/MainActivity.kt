package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.ui.HomeMenuTheme
import com.alorma.homemenu.widgets.daysList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMenuTheme {
                HomeScreen(
                    data = mainViewModel.days(),
                    onDayClick = { day ->
                        mainViewModel.onDayClicked(day)
                    },
                    onAddDay = { mainViewModel.onAddNewDay() }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(
    data: StateFlow<List<Day>>,
    onDayClick: (Day) -> Unit,
    onAddDay: () -> Unit,
) {
    val bodyShape: Shape = MaterialTheme.shapes.large.copy(
        topLeft = CornerSize(16.dp),
        topRight = CornerSize(0.dp),
        bottomLeft = CornerSize(0.dp),
        bottomRight = CornerSize(0.dp)
    )
    val backDropState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    BackdropScaffold(
        scaffoldState = backDropState,
        appBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                navigationIcon = { navigationIcon(backDropState) },
                title = { Text(text = title) },
                actions = {
                    IconButton(onClick = onAddDay) {
                        Icon(asset = vectorResource(id = R.drawable.ic_add))
                    }
                }
            )
        },
        backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
        backLayerContent = {
            backLayer()
        },
        frontLayerShape = bodyShape,
    ) {
        homeComponent(data = data, onDayClick = onDayClick)
    }
}

@Composable
private fun backLayer() {
    Box(
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Center
    ) {
        Text("back layer")
    }
}

@ExperimentalMaterialApi
@Composable
private fun navigationIcon(backDropState: BackdropScaffoldState) {
    IconButton(onClick = {
        if (backDropState.isRevealed) {
            backDropState.conceal()
        } else {
            backDropState.reveal()
        }
    }) {
        val asset = if (backDropState.isRevealed) {
            vectorResource(id = R.drawable.ic_nav_expand_conceal)
        } else {
            vectorResource(id = R.drawable.ic_nav_expand_reveal)
        }
        Icon(asset = asset)
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
private fun homeComponent(
    data: StateFlow<List<Day>>,
    onDayClick: (Day) -> Unit,
) {
    val items = data.collectAsState().value

    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center
        ) {
            Text("No items")
        }
    } else {
        daysList(days = items, onDayClick = onDayClick)
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeMenuTheme {
        HomeScreen(
            data = MutableStateFlow(emptyList()),
            onAddDay = {},
            onDayClick = {},
        )
    }
}