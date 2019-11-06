package com.example.clashroyale.repositories;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.utilits.Action;
import com.example.clashroyale.utilits.ActionT;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final Api mApi;

    private List<Card> mCache = new ArrayList<>();

    public Repository(Api api) {
        mApi = api;
    }

    public void newDeck(@NonNull final ActionT<List<Card>> callback, @NonNull final Action error) {
        mCache = new ArrayList<>();

        mApi.RandomDeck((cards) -> {
            mCache = cards;
            callback.execute(cards);
        }, error);
    }

    public List<Card> getDeck() {
        return mCache;
    }
}
