package com.alorma.homemenu.base

import androidx.compose.runtime.Composable
import com.alorma.drawer_base.DebugModule
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule

object DebugModulesProvider {

    @Composable
    operator fun invoke(): List<DebugModule> = listOf(
        BuildModule(),
        DeviceModule()
    )
}