package com.example.myapplication.data.service

import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.domain.utils.Constants.Companion.ATM_QUERY
import com.example.myapplication.domain.utils.Constants.Companion.BANK_QUERY
import com.example.myapplication.domain.utils.Constants.Companion.KIOSK_QUERY
import com.example.myapplication.domain.utils.Constants.Companion.QUERY
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {
    @GET(ATM_QUERY)
    fun getATMs(@Query(QUERY) city: String): Single<List<ItemModel>>
    @GET(KIOSK_QUERY)
    fun getKiosks(@Query(QUERY) city: String): Single<List<ItemModel>>
    @GET(BANK_QUERY)
    fun getBanks(@Query(QUERY) city: String): Single<List<ItemModel>>
}