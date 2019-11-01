package com.example.clashroyale.application;

import com.example.clashroyale.activities.card.CardActivity;
import com.example.clashroyale.activities.deck.DeckActivity;
import com.example.clashroyale.api.ApiModule;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface AppComponent {
    void InjectsMainActivity(DeckActivity deckActivity);
    void InjectsCardActivity(CardActivity cardActivity);
}
