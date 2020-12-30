package com.android.myapplication.movies

import android.app.Application
import com.android.myapplication.movies.di.apiModule
import com.android.myapplication.movies.di.databaseModule
import com.android.myapplication.movies.di.repositoryModule
import com.android.myapplication.movies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    apiModule,
                    viewModelModule,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }
}