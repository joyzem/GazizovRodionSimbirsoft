package com.example.androidpractice.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.mvi.AsyncFeature
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun Disposable.addToCompositeDisposable() {
        compositeDisposable.add(this)
    }

    protected fun <State, Wish, News> AsyncFeature<State, Wish, News>.newWish(wish: Wish) {
        newWish(viewModelScope, wish)
    }
}
