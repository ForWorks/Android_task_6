package com.example.myapplication.data.repository

import com.example.myapplication.data.model.ItemModel
import io.reactivex.rxjava3.core.Single

interface IRepository {
    fun getATMs(city: String): Single<List<ItemModel>>
    fun getKiosks(city: String): Single<List<ItemModel>>
    fun getBanks(city: String): Single<List<ItemModel>>
}