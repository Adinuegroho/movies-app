package com.bagus.moviesapp

import android.app.Application
import com.bagus.core.di.databaseModule
import com.bagus.core.di.networkModule
import com.bagus.core.di.repositoryModule
import com.bagus.moviesapp.di.useCaseModule
import com.bagus.moviesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}