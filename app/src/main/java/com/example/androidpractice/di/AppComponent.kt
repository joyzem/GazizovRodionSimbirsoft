package com.example.androidpractice.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.MainActivity
import com.example.androidpractice.core.data.di.AssetsModule
import com.example.androidpractice.core.data.di.DataModule
import com.example.androidpractice.core.data.di.InteractorModule
import com.example.androidpractice.core.database.di.DatabaseModule
import com.example.androidpractice.core.di.ViewModelModule
import com.example.androidpractice.core.network.di.NetworkModule
import com.example.androidpractice.core.network.di.RetrofitModule
import com.example.androidpractice.feature.auth.di.AuthDeps
import com.example.androidpractice.feature.auth.di.AuthDiModule
import com.example.androidpractice.feature.help.di.HelpDeps
import com.example.androidpractice.feature.help.di.HelpDiModule
import com.example.androidpractice.feature.news.di.NewsDeps
import com.example.androidpractice.feature.news.di.NewsDiModule
import com.example.androidpractice.feature.profile.di.ProfileDeps
import com.example.androidpractice.feature.profile.di.ProfileDiModule
import com.example.androidpractice.feature.search.di.SearchDeps
import com.example.androidpractice.feature.search.di.SearchDiModule
import dagger.BindsInstance
import dagger.Component
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
        SearchDiModule::class,
        ProfileDiModule::class,
        NewsDiModule::class,
        MainModule::class,
    ]
)
interface AppComponent : AuthDeps, HelpDeps, ProfileDeps, SearchDeps, NewsDeps {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    override val viewModelFactory: ViewModelProvider.Factory
}
