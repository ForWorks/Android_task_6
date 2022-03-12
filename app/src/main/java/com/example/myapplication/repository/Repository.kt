package com.example.myapplication.repository

import com.example.myapplication.model.ItemModel
import com.example.myapplication.service.ItemService
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Constants.Companion.POINT
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class Repository @Inject constructor(private val service: ItemService) {
    fun collectItems(): Observable<ItemModel> {
        val single = Single.zip(
            service.getATMs(Constants.GOMEL),
            service.getKiosks(Constants.GOMEL),
            service.getBanks(Constants.GOMEL),
            { atms, kiosks, banks ->
                atms.forEach { it.type = 0 }
                kiosks.forEach { it.type = 1 }
                banks.forEach { it.type = 2 }
                atms + kiosks + banks
            })
            .map { list ->
                list.sortedWith(
                    compareBy {
                        sqrt((POINT.latitude - it.x).pow(2) + (POINT.longitude - it.y).pow(2)) })
                }
            .flatMapObservable { Observable.fromIterable(it) }
            .distinct()
            .take(Constants.COUNT)
        return single
    }
}