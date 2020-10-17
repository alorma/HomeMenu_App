package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.ui.HomeMenuTheme
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.getViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMenuTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.surface) {
                    DateText()
                }
            }
        }
    }
}

@Composable
fun DateText(
    mainViewModel: MainViewModel = getViewModel(),
) {
    Text(text = "It's: ${runBlocking { mainViewModel.getTime() }}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val clock = object : Clock {
        override suspend fun now(): Long = System.currentTimeMillis()
    }
    val mainViewModel = MainViewModel(clock = clock)
    HomeMenuTheme {
        DateText(mainViewModel = mainViewModel)
    }
}