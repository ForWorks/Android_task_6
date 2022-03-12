package com.example.myapplication.service

import com.example.myapplication.model.ItemModel
import com.example.myapplication.utils.Constants.Companion.ATM_URL
import com.example.myapplication.utils.Constants.Companion.BANK_URL
import com.example.myapplication.utils.Constants.Companion.KIOSK_URL
import com.example.myapplication.utils.Constants.Companion.QUERY
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {
    @GET(ATM_URL)
    fun getATMs(@Query(QUERY) city: String): Single<List<ItemModel>>
    @GET(KIOSK_URL)
    fun getKiosks(@Query(QUERY) city: String): Single<List<ItemModel>>
    @GET(BANK_URL)
    fun getBanks(@Query(QUERY) city: String): Single<List<ItemModel>>
}