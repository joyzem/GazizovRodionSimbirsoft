package com.example.androidpractice.data.categories

import android.util.Log
import com.example.androidpractice.data.categories.dto.toModel
import com.example.androidpractice.data.categories.network.CategoriesRetrofitApi
import com.example.androidpractice.domain.categories.api.CategoriesApi
import com.example.androidpractice.domain.categories.model.Category
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "CategoriesApiImpl"

@Singleton
class CategoriesApiImpl @Inject constructor(
    private val api: CategoriesRetrofitApi
) : CategoriesApi {

    override fun fetchCategories(): Observable<List<Category>> {
        Log.d(TAG, "fetchCategories: started")
        return api.fetchCategories().map { categories ->
            categories.map { dto ->
                dto.toModel()
            }
        }
    }
}
