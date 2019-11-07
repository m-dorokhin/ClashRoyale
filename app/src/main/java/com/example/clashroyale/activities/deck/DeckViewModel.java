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
import com.example.clashroyale.models.CardView;
import com.example.clashroyale.repositories.Repository;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private final Repository mRepository;

    public final ObservableField<List<CardView>> cards = new ObservableField<>(new ArrayList<>());
    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DeckViewModel(@NonNull Application application, Repository repository) {
        super(application);
        mRepository = repository;

        List<CardView> cachedCards = mRepository.getDeck();
        cards.set(cachedCards);
        if (cachedCards.size() > 0) {
            averageElixir.set(cachedCards.stream()
                    .mapToInt((card) -> card.elixirCost)
                    .average()
                    .getAsDouble());
        } else {
            requestDeck();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestDeck() {
        if (requested.get())
            return;

        requested.set(true);
        cards.set(new ArrayList<>());
        averageElixir.set(0);

        mRepository.newDeck(
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
