package com.example.androidpractice.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.MainActivity
import com.example.androidpractice.core.data.di.DataModule
import com.example.androidpractice.core.di.ViewModelModule
import com.example.androidpractice.feature.auth.di.AuthDeps
import com.example.androidpractice.feature.help.di.HelpDeps
import com.example.androidpractice.feature.news.di.NewsDeps
import com.example.androidpractice.feature.news_details_impl.di.NewsDetailsDeps
import com.example.androidpractice.feature.profile.di.ProfileDeps
import com.example.androidpractice.feature.search.di.SearchDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        CoreModules::class,
        ViewModelModule::class,
        ViewModelsDiModule::class,
        MainModule::class,
        FeaturesInjectorModule::class
    ]
)
interface AppComponent : AuthDeps, HelpDeps, ProfileDeps, SearchDeps, NewsDeps, NewsDetailsDeps {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    override val viewModelFactory: ViewModelProvider.Factory
}
