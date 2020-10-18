package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.ui.HomeMenuTheme
import com.alorma.homemenu.widgets.dayRow
import com.alorma.homemenu.widgets.daysList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMenuTheme {
                HomeScreen(mainViewModel)
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(mainViewModel: MainViewModel) {
    Scaffold(
        modifier = Modifier.background(
            color = MaterialTheme.colors.background
        ),
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add") },
                shape = RoundedCornerShape(percent = 50),
                onClick = { mainViewModel.onAddNewDay() },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        bodyContent = {
            homeComponent(mainViewModel) { day ->
                mainViewModel.onDayClicked(day = day)
            }
        }
    )
}

@ExperimentalCoroutinesApi
@Composable
private fun homeComponent(
    mainViewModel: MainViewModel,
    onDayClick: (Day) -> Unit,
) {
    val items = mainViewModel.days().collectAsState().value
    daysList(days = items, onDayClick = onDayClick)
}

@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = MainViewModel(
        object : Clock {
            override suspend fun getToday(): LocalDate = LocalDate.now()
            override suspend fun now(): Long = System.currentTimeMillis()
        }
    )
    HomeMenuTheme {
        HomeScreen(viewModel)
    }
}