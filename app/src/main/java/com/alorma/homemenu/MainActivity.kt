package com.alorma.homemenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.alorma.homemenu.screen.CreateDayScreen
import com.alorma.homemenu.screen.HomeScreen
import com.alorma.homemenu.ui.HomeMenuTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeMenuTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable(route = "home") {
                        HomeScreen(
                            onAddDay = {
                                navController.navigate("create_day")
                            }
                        )
                    }
                    composable(route = "create_day") {
                        CreateDayScreen(navController = navController)
                    }
                }
            }
        }
    }
}