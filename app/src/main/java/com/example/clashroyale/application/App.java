package com.example.clashroyale.application;

import android.app.Application;

import com.example.clashroyale.activities.ActivityModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .build();
    }
}
