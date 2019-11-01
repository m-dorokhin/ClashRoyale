package com.example.clashroyale.application;

import com.example.clashroyale.CardActivity;
import com.example.clashroyale.MainActivity;

import dagger.Component;

@Component(modules = {})
public interface AppComponent {
    void InjectsMainActivity(MainActivity mainActivity);
    void InjectsCardActivity(CardActivity cardActivity);
}
