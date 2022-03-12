package com.example.myapplication.utils

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

        const val ATM_URL = "atm"
        const val BANK_URL = "infobox"
        const val KIOSK_URL = "filials_info"
        const val BASE_URL = "https://belarusbank.by/api/"

        const val COUNT = 10L
        val POINT = LatLng(52.425163, 31.015039)
    }
}