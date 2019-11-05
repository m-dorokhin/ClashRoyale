package com.example.clashroyale.activities.deck;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends ViewModel {
    private final Api mApi;

    public final ObservableField<List<Card>> cards = new ObservableField<>(new ArrayList<>());
    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    public DeckViewModel(Api api) {
        mApi = api;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestDeck() {
        if (requested.get())
            return;

        requested.set(true);
        mApi.RandomDeck(
                (receivedCards) -> {
                    cards.set(receivedCards);
                    requested.set(false);
                    averageElixir.set(receivedCards.stream()
                            .mapToInt((card) -> card.elixirCost)
                            .average()
                            .getAsDouble());
                },
                () -> {
                    requested.set(false);
                });
    }
}
