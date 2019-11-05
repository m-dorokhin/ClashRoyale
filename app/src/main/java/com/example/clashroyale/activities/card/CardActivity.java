package com.example.clashroyale.activities.card;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clashroyale.R;
import com.example.clashroyale.api.Api;
import com.example.clashroyale.application.App;
import com.example.clashroyale.ui.cardFlipper.CardFlipper;

import javax.inject.Inject;

public class CardActivity extends AppCompatActivity {

    private CardFlipper cardFlipper;

    @Inject
    public Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        App.getComponent().InjectsCardActivity(this);

        cardFlipper = findViewById(R.id.card_flipper);
        api.RandomDeck((cards) -> cardFlipper.setItems(cards), () -> {});
    }
}
