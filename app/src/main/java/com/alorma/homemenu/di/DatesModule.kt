package com.alorma.homemenu.di

import com.alorma.homemenu.time.Clock
import com.alorma.homemenu.time.NativeClock
import org.koin.dsl.module

object DatesModule {
    operator fun invoke() = module {
        single<Clock> { NativeClock() }
    }
}