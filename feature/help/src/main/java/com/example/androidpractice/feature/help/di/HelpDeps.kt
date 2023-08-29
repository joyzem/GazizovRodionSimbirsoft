package com.example.androidpractice.feature.help.di

import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import kotlin.properties.Delegates.notNull

interface HelpDeps {
    val viewModelFactory: ViewModelProvider.Factory
    val categoriesRepo: CategoriesRepo
}

internal interface HelpDepsProvider {
    val deps: HelpDeps

    companion object : HelpDepsProvider by HelpDepsStore
}

object HelpDepsStore : HelpDepsProvider {
    override var deps: HelpDeps by notNull()
}
