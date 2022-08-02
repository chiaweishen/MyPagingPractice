package com.scw.mypagingpractice

import android.app.Application
import com.facebook.stetho.Stetho
import com.scw.mypagingpractice.di.module.*
import com.scw.mypagingpractice.util.MyDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initStetho()
        initTimber()
        initKoin()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(dbModule, mediatorModule, apiModule, repoModule, viewModelModule)
        }
    }
}