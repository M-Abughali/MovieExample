package com.mj.movieexample.di.module

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
class AppModule(val application: Application) {
    @Provides
    fun provideApplication(): Application {
        return application
    }


}