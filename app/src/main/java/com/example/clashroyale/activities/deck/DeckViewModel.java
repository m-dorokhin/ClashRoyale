package com.example.clashroyale.activities.deck;

import androidx.lifecycle.ViewModel;

import com.example.clashroyale.api.Api;

public class DeckViewModel extends ViewModel {
    private final Api mApi;

    public DeckViewModel(Api api) {
        mApi = api;
    }
}
