package com.example.clashroyale.activities;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.clashroyale.activities.card.CardViewModel;
import com.example.clashroyale.activities.deck.DeckViewModel;
import com.example.clashroyale.services.NetStatusReceiver;
import com.example.clashroyale.services.Repository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Repository mRepository;
    private final Application mApplication;
    private final NetStatusReceiver mNetStatusReceiver;

    public ViewModelFactory(
            Application Application,
            Repository repository,
            NetStatusReceiver netStatusReceiver) {
        mApplication = Application;
        mRepository = repository;
        mNetStatusReceiver = netStatusReceiver;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DeckViewModel.class)
            return (T) new DeckViewModel(mApplication, mRepository, mNetStatusReceiver);

        if (modelClass == CardViewModel.class)
            return (T) new CardViewModel(mRepository);

        Log.e("ViewModelFactory", "Can't create " + modelClass.getName());
        return null;
    }
}
