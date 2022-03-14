package com.example.myapplication.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.domain.collector.ItemCollector
import com.example.myapplication.domain.utils.Constants.Companion.ERROR
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val collector: ItemCollector): ViewModel() {

    var items: MutableLiveData<List<ItemModel>> = MutableLiveData()

    fun fillItemList() {
        val items = mutableListOf<ItemModel>()
        collector.collectItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> items.add(value) },
                { error -> Log.e(ERROR, "$error") },
                { this.items.value = items })
    }
}