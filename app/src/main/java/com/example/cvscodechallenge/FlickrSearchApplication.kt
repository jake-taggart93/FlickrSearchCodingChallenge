package com.example.cvscodechallenge

import android.app.Application
import com.example.cvscodechallenge.data.di.dataModule
import com.example.cvscodechallenge.domain.di.domainModule
import com.example.cvscodechallenge.ui.di.appModule
import org.koin.core.context.startKoin

class FlickrSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Configure Koin modules
        startKoin {
            modules(dataModule, domainModule, appModule)
        }
    }
}