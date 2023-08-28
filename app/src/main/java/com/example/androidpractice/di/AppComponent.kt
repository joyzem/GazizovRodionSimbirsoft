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
import com.example.androidpractice.screen.help.di.HelpComponent
import com.example.androidpractice.screen.news.GetEventsService
import com.example.androidpractice.screen.news.di.NewsComponent
import com.example.androidpractice.screen.profile.di.ProfileComponent
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
        MainModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent : AuthDeps {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(service: GetEventsService)

    fun helpSubcomponent(): HelpComponent.Factory
    fun newsSubcomponent(): NewsComponent.Factory
    fun profileSubcomponent(): ProfileComponent.Factory
    fun searchSubcomponent(): SearchComponent.Factory

    override val viewModelFactory: ViewModelProvider.Factory
}

@Module(
    subcomponents = [
        NewsComponent::class,
        HelpComponent::class,
        ProfileComponent::class,
        SearchComponent::class
    ]
)
interface SubcomponentsModule
