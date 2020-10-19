package com.alorma.homemenu.di

import com.alorma.homemenu.base.StringsProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object OthersModule {
    operator fun invoke() = module {
        factory { StringsProvider(androidContext()) }
    }
}