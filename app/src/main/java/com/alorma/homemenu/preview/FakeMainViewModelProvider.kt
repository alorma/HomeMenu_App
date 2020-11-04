package com.alorma.homemenu.preview

import androidx.ui.tooling.preview.PreviewParameterProvider
import com.alorma.homemenu.base.StringsProvider
import com.alorma.homemenu.main.MainViewModel
import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.time.NativeClock
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FakeMainViewModelProvider : PreviewParameterProvider<MainViewModel> {

    private val clock: Clock = NativeClock()
    private val stringsProvider: StringsProvider = object : StringsProvider {
        override fun getString(id: Int): String = "Fake"
    }

    override val values: Sequence<MainViewModel> = sequenceOf(
        MainViewModel(
            clock = clock,
            stringsProvider = stringsProvider,
        )
    )
}