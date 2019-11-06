package com.example.clashroyale.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.clashroyale.activities.card.CardViewModel;
import com.example.clashroyale.activities.deck.DeckViewModel;
import com.example.clashroyale.api.Api;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Api mApi;
    private final Application mApplication;

    public ViewModelFactory(Application Application, Api api) {
        mApplication = Application;
        mApi = api;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DeckViewModel.class)
            return (T) new DeckViewModel(mApplication, mApi);

        if (modelClass == CardViewModel.class)
            return (T) new CardViewModel(mApi);

        Log.e("ViewModelFactory", "Can't create " + modelClass.getName());
        return null;
    }
}
