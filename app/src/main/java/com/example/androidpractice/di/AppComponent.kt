package com.example.androidpractice.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.MainActivity
import com.example.androidpractice.core.data.di.AssetsModule
import com.example.androidpractice.core.data.di.DataModule
import com.example.androidpractice.core.database.di.DatabaseModule
import com.example.androidpractice.core.di.ViewModelModule
import com.example.androidpractice.core.network.di.NetworkModule
import com.example.androidpractice.core.network.di.RetrofitModule
import com.example.androidpractice.feature.auth.di.AuthDeps
import com.example.androidpractice.feature.auth.di.AuthDiModule
import com.example.androidpractice.feature.help.di.HelpDeps
import com.example.androidpractice.feature.help.di.HelpDiModule
import com.example.androidpractice.feature.profile.di.ProfileDeps
import com.example.androidpractice.feature.profile.di.ProfileDiModule
import com.example.androidpractice.screen.news.GetEventsService
import com.example.androidpractice.screen.news.di.NewsComponent
import com.example.androidpractice.screen.search.di.SearchComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        AssetsModule::class,
        NetworkModule::class,
        RetrofitModule::class,
        DatabaseModule::class,
        InteractorModule::class,
        ViewModelModule::class,
        AuthDiModule::class,
        HelpDiModule::class,
        ProfileDiModule::class,
        MainModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent : AuthDeps, HelpDeps, ProfileDeps {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(service: GetEventsService)

    fun newsSubcomponent(): NewsComponent.Factory
    fun searchSubcomponent(): SearchComponent.Factory

    override val viewModelFactory: ViewModelProvider.Factory
}

@Module(
    subcomponents = [
        NewsComponent::class,
        SearchComponent::class
    ]
)
interface SubcomponentsModule
