package com.example.myapplication.di.components

import com.example.myapplication.di.modules.NetworkModule
import com.example.myapplication.di.modules.ViewModelModule
import com.example.myapplication.view.MapActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MapActivity)
}