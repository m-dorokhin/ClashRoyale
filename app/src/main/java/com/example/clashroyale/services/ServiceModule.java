package com.example.clashroyale.services;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Provides
    public ImageLoader provideImageLoader() {
        return new ImageLoader();
    }

    @Singleton
    @Provides
    public Repository provideRepository(@NonNull Api api, @NonNull ImageLoader imageLoader) {
        return new Repository(api, imageLoader);
    }

    @Provides
    public NetStatusReceiver provideNetStatusReceiver() {
        return new NetStatusReceiver();
    }
}
