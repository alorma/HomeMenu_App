package com.alorma.homemenu.main

import androidx.lifecycle.ViewModel
import com.alorma.homemenu.time.Clock

class MainViewModel(private val clock: Clock) : ViewModel() {

    suspend fun getTime() = clock.now()
}