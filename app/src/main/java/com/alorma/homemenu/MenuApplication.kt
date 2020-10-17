package com.alorma.homemenu

import android.app.Application
import com.alorma.homemenu.di.DatesModule
import com.alorma.homemenu.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MenuApplication)

            modules(
                DatesModule(),
                ViewModelModule()
            )
        }
    }
} 