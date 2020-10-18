package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.ui.HomeMenuTheme
import com.alorma.homemenu.widgets.dayRow
import com.alorma.homemenu.widgets.daysList
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
        modifier = Modifier.background(
            color = MaterialTheme.colors.background
        ),
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        bodyContent = {
            val modifier = Modifier.fillMaxWidth()
                .padding(8.dp)

            ScrollableColumn(
                modifier = modifier,
            ) {
                val days = runBlocking { viewModel.getDays() }
                daysList(days = days)
            }
        }
    )
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