package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ItemModel
import com.example.myapplication.repository.Repository
import com.example.myapplication.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(repository: Repository): ViewModel() {

    private var items: MutableLiveData<List<ItemModel>> = MutableLiveData()

    init {
        val items = mutableListOf<ItemModel>()
        repository.collectItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> items.add(value) },
                { error -> Log.e(Constants.ERROR, "$error") },
                { this.items.value = items })
    }

    fun getItems(): MutableLiveData<List<ItemModel>> = items
}