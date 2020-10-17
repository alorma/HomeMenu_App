package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.ui.HomeMenuTheme
import kotlinx.coroutines.runBlocking
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

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        bodyContent = {
            val modifier = Modifier.fillMaxWidth()

            ScrollableColumn(
                modifier = modifier,
            ) {
                runBlocking { viewModel.getDays() }.forEach { day ->
                    dayRow(day)
                }
            }
        }
    )
}

@Composable
private fun dayRow(day: Day) {
    Row(
        modifier = Modifier.padding(all = 8.dp)
    ) {
        if (day.isToday) {
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                Text(text = day.name)
            }
        } else {
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                Text(text = day.name)
            }
        }
    }
}

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