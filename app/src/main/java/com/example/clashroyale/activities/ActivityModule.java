package com.example.clashroyale.activities;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.clashroyale.application.App;
import com.example.clashroyale.services.Repository;

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
            @NonNull Application application, @NonNull Repository repository) {
        return new ViewModelFactory(application, repository);
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
