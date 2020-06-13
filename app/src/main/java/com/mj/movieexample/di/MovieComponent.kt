package com.mj.movieexample.di

import com.mj.movieexample.ui.MainActivity
import com.mj.movieexample.di.module.AppModule
import com.mj.movieexample.di.module.RepositoryModule
import com.mj.movieexample.di.module.RetrofitModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RepositoryModule::class])
interface MovieComponent {
    fun Inject(mainActivity: MainActivity);
}