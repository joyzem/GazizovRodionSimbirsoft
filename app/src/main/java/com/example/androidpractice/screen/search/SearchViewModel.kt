package com.example.androidpractice.screen.search

import com.example.androidpractice.ui.BaseViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val _queries: Subject<CharSequence> = PublishSubject.create()
    val queries = _queries
        .debounce(500, TimeUnit.MILLISECONDS)
        .map { it.toString() }

    fun newQuery(query: CharSequence) {
        _queries.onNext(query)
    }
}