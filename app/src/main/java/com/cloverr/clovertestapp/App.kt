package com.cloverr.clovertestapp

import android.app.Application
import com.cloverr.clovertestapp.di.AppComponent
import com.cloverr.clovertestapp.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}