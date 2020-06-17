package com.mj.movieexample.di.module;

import com.mj.movieexample.network.RxSingleSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    public RxSingleSchedulers providesScheduler() {
        return RxSingleSchedulers.DEFAULT;
    }
}
