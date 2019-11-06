package com.example.clashroyale.activities.deck;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.example.clashroyale.activities.card.CardActivity;
import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private final Api mApi;

    public final ObservableField<List<Card>> cards = new ObservableField<>(new ArrayList<>());
    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    public DeckViewModel(@NonNull Application application, Api api) {
        super(application);
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

    public void gotoCard(int cardNo) {
        Log.i("DeckViewModel", "Selected card: " + cardNo);
        Context context = getApplication();
        Intent intent = new Intent(context, CardActivity.class);
        intent.putExtra(CardActivity.EXTRA_CARD_NO, cardNo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Без этого флага не запускает
        context.startActivity(intent);
    }
}
