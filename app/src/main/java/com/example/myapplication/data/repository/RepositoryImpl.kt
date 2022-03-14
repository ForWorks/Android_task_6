package com.example.myapplication.data.repository

import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.data.service.ItemService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: ItemService): IRepository {
    override fun getATMs(city: String): Single<List<ItemModel>> =
        service.getATMs(city)
    override fun getKiosks(city: String): Single<List<ItemModel>> =
        service.getKiosks(city)
    override fun getBanks(city: String): Single<List<ItemModel>> =
        service.getBanks(city)
}