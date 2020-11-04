package com.alorma.homemenu.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.DrawerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alorma.homemenu.base.DebugModulesProvider
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.ModuleExpandedState
import com.alorma.homemenu.BuildConfig

private val DarkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color(0xFF121212)
)

private val LightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color(0xFFF4F4F4)
)

@Composable
fun HomeMenuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable (DrawerState) -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = {
            DebugDrawerLayout(
                isDebug = { BuildConfig.DEBUG },
                initialModulesState = ModuleExpandedState.COLLAPSED,
                drawerModules = { DebugModulesProvider() },
                bodyContent = content
            )
        }
    )
}