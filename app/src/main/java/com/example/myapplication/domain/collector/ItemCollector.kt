package com.example.myapplication.domain.collector

import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.data.service.ItemService
import com.example.myapplication.domain.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class ItemCollector @Inject constructor(private val service: ItemService) {
    fun collectItems(): Observable<ItemModel> {
        val single = Single.zip(
            service.getATMs(Constants.GOMEL),
            service.getKiosks(Constants.GOMEL),
            service.getBanks(Constants.GOMEL),
            { atms, kiosks, banks ->
                atms.forEach { it.type = Constants.ATM }
                kiosks.forEach { it.type = Constants.KIOSK }
                banks.forEach { it.type = Constants.BANK }
                atms + kiosks + banks
            })
            .map { list ->
                list.sortedWith(
                    compareBy {
                        sqrt((Constants.POINT.latitude - it.x).pow(2) + (Constants.POINT.longitude - it.y).pow(2)) })
            }
            .flatMapObservable { Observable.fromIterable(it) }
            .distinct()
            .take(Constants.COUNT)
        return single
    }
}