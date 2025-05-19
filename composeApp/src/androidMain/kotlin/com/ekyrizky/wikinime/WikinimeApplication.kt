package com.ekyrizky.wikinime

import android.app.Application
import com.ekyrizky.wikinime.di.initKoin
import org.koin.android.ext.koin.androidContext

class WikinimeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WikinimeApplication)
        }
    }
}