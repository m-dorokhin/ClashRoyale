package com.example.clashroyale.repositories;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.clashroyale.api.Api;
import com.example.clashroyale.models.CardView;
import com.example.clashroyale.utilits.Action;
import com.example.clashroyale.utilits.ActionT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private final Api mApi;
    private final ImageLoader mImageLoader;

    private List<CardView> mCache = new ArrayList<>();

    public Repository(Api api, ImageLoader imageLoader) {
        mApi = api;
        mImageLoader = imageLoader;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void newDeck(@NonNull final ActionT<List<CardView>> callback, @NonNull final Action error) {
            mCache = new ArrayList<>();

            mApi.RandomDeck((cards) -> {
            mCache = cards.stream()
                    .map(card -> new CardView(card))
                    .collect(Collectors.toList());

            mImageLoader.load(mCache, callback);
        }, error);
    }

    public List<CardView> getDeck() {
        return mCache;
    }
}
