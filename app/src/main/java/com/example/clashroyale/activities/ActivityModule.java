package com.example.clashroyale.activities;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.application.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final App mApplication;

    public ActivityModule(App application) {
        mApplication = application;
    }

    @Provides
    public ViewModelFactory provideViewModelFactory(
            @NonNull Application application, @NonNull Api api) {
        return new ViewModelFactory(application, api);
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
