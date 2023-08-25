package com.example.androidpractice.di

import android.content.Context
import com.example.androidpractice.MainActivity
import com.example.androidpractice.screen.auth.di.AuthComponent
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
        MainModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(service: GetEventsService)

    fun authSubcomponent(): AuthComponent.Factory
    fun helpSubcomponent(): HelpComponent.Factory
    fun newsSubcomponent(): NewsComponent.Factory
    fun profileSubcomponent(): ProfileComponent.Factory
    fun searchSubcomponent(): SearchComponent.Factory
}


@Module(
    subcomponents = [
        AuthComponent::class,
        NewsComponent::class,
        HelpComponent::class,
        ProfileComponent::class,
        SearchComponent::class
    ]
)
interface SubcomponentsModule