package com.example.clashroyale.services;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.api.models.Arena;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.models.CardView;
import com.example.clashroyale.utilits.Action;
import com.example.clashroyale.utilits.ActionT;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final Api mApi;
    private final ImageLoader mImageLoader;

    private List<CardView> mCache = new ArrayList<>();
    private List<Arena> mArenas = new ArrayList<>();

    public Repository(@NonNull Api api, ImageLoader imageLoader) {
        mApi = api;
        mImageLoader = imageLoader;
    }

    public void newDeck(
            @NonNull final ActionT<List<CardView>> callback,
            @NonNull final Action error) {
        mCache = new ArrayList<>();

        getArenas((arenas) -> {
            mApi.RandomDeck((cards) -> {
                mCache.clear();
                for (Card card: cards) {
                    CardView cardView = new CardView(card);
                    cardView.arenaName = arenas.get(card.arena).name;
                    cardView.victoryGold = arenas.get(card.arena).victoryGold;
                    mCache.add(cardView);
                }

                mImageLoader.load(mCache, callback);
            }, error);
        });
    }

    public void getArenas(ActionT<List<Arena>> callback) {
         if (mArenas.isEmpty()) {
             mApi.Arenas((arenas) -> {
                 mArenas = arenas;
                 callback.execute(mArenas);
             }, null);
         } else {
             callback.execute(mArenas);
         }
    }

    public List<CardView> getDeck() {
        return mCache;
    }
}
