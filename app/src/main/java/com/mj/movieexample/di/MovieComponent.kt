package com.mj.movieexample.di

import com.mj.movieexample.core.MyApp
import com.mj.movieexample.di.module.AppModule
import com.mj.movieexample.di.module.RepositoryModule
import com.mj.movieexample.di.module.RetrofitServiceModule
import com.mj.movieexample.di.module.ViewModelModule
import com.mj.movieexample.ui.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class, RetrofitServiceModule::class, RepositoryModule::class, ViewModelModule::class
    ]
)
interface MovieComponent {
    fun inject(mainActivity: MainActivity)
}