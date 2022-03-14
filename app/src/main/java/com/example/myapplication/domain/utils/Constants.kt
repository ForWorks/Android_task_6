package com.example.myapplication.domain.utils

import com.google.android.gms.maps.model.LatLng

class Constants {
    companion object {
        const val ERROR = "ERROR"
        const val QUERY = "city"

        const val GOMEL = "Гомель"
        const val CENTER = "Центр"
        const val KIOSK = "Инфокиоск"
        const val BANK = "Банк"
        const val ATM = "Банкомат"
        const val CHECK_CONNECTION = "Проверьте интернет соединение"

        const val ATM_QUERY = "atm"
        const val BANK_QUERY = "infobox"
        const val KIOSK_QUERY = "filials_info"
        const val BASE_URL = "https://belarusbank.by/api/"

        const val COUNT = 10L
        val POINT = LatLng(52.425163, 31.015039)
    }
}