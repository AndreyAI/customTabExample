package com.example.customtab

import android.app.Application
import com.example.customtab.data.db.Database
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}