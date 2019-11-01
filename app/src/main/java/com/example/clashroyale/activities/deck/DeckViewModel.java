package com.example.clashroyale.activities.deck;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.lifecycle.ViewModel;

import com.example.clashroyale.api.Api;

public class DeckViewModel extends ViewModel {
    private final Api mApi;

    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    public DeckViewModel(Api api) {
        mApi = api;
    }

    public void requestDeck() {
        if (requested.get())
            return;

        requested.set(true);
    }
}
