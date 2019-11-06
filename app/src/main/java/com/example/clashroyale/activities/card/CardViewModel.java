package com.example.clashroyale.activities.card;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Card;

import java.util.ArrayList;
import java.util.List;

public class CardViewModel extends ViewModel {
    private final Api mApi;

    public final ObservableField<List<Card>> cards = new ObservableField<>(new ArrayList<>());

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public CardViewModel(Api api) {
        mApi = api;

        mApi.RandomDeck((receivedCards) -> { cards.set(receivedCards); }, () -> { });
    }
}
