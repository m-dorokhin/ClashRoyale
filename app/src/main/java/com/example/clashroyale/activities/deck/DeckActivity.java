package com.example.clashroyale.activities.deck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clashroyale.R;
import com.example.clashroyale.application.App;

public class DeckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().InjectsMainActivity(this);
    }
}
