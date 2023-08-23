package com.example.androidpractice.screen.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val rxCategories: Observable<List<Category>> = categoriesRepo.fetchCategories()

    init {
        rxCategories
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { categories ->
                _categories.value = categories
            }
            .addToCompositeDisposable()
    }
}
