package com.scw.mypagingpractice

import android.app.Application
import com.scw.mypagingpractice.di.module.apiModule
import com.scw.mypagingpractice.util.MyDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(apiModule))
        }
    }
}