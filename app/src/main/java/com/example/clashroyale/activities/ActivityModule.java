package com.example.clashroyale.activities;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    @Provides
    public ViewModelFactory provideViewModelFactory(@NonNull Api api) {
        return new ViewModelFactory(api);
    }
}
