package com.alorma.homemenu.di

import com.alorma.homemenu.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    operator fun invoke() = module {
        viewModel<MainViewModel> {
            MainViewModel(get())
        }
    }
}