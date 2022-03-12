package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.di.components.AppComponent
import com.example.myapplication.di.components.DaggerAppComponent

class App: Application() {

    private val component by lazy { DaggerAppComponent.create() }

    fun getAppComponent(): AppComponent = component
}