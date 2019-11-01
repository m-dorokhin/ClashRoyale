package com.example.clashroyale.activities.deck;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Card;

import java.util.List;

public class DeckViewModel extends ViewModel {
    private final Api mApi;

    private final MutableLiveData<List<Card>> mCards = new MutableLiveData<>();
    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    public LiveData<List<Card>> getCards() {
        return mCards;
    }

    public DeckViewModel(Api api) {
        mApi = api;
    }

    public void requestDeck() {
        if (requested.get())
            return;

        requested.set(true);
        mApi.RandomDeck(
                (cards) -> {
                    mCards.postValue(cards);
                    requested.set(false);
                },
                () -> {
                    requested.set(false);
                });
    }
}
