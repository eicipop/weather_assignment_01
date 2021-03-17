package com.example.weather_assignment_01.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()
    init {
        isLoading.value = false
    }
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
        hideLoading()
    }

    fun showLoading(){
        isLoading.value = true
    }
    fun hideLoading(){
        isLoading.value = false
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}