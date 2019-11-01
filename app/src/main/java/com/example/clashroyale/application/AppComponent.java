package com.example.clashroyale.application;

import com.example.clashroyale.CardActivity;
import com.example.clashroyale.MainActivity;
import com.example.clashroyale.api.ApiModule;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface AppComponent {
    void InjectsMainActivity(MainActivity mainActivity);
    void InjectsCardActivity(CardActivity cardActivity);
}
