package com.mj.movieexample.di

import android.app.Application
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.di.module.AppModule
import com.mj.movieexample.di.module.RepositoryModule
import com.mj.movieexample.di.module.RetrofitServiceModule
import com.mj.movieexample.di.module.ViewModelModule
import com.mj.movieexample.ui.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [RetrofitServiceModule::class, RepositoryModule::class, ViewModelModule::class
    ]
)
interface MovieComponent {
    fun inject(mainActivity: MainActivity)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun injectApplication(application: Application): Builder;

        fun build(): MovieComponent;
    }
}