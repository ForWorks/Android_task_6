package com.example.myapplication.presentation.di.modules

import com.example.myapplication.data.service.ItemService
import com.example.myapplication.domain.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ItemService =
        retrofit.create(ItemService::class.java)
}