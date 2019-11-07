package com.example.clashroyale.activities.deck;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.clashroyale.R;
import com.example.clashroyale.activities.ViewModelFactory;
import com.example.clashroyale.application.App;
import com.example.clashroyale.databinding.ActivityDeckBinding;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class DeckActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getComponent().InjectsMainActivity(this);

        DeckViewModel deckViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(DeckViewModel.class);
        ActivityDeckBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_deck);
        binding.setDeck(deckViewModel);

        deckViewModel.getSnackMessage().observe(this, s -> Snackbar
                .make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                .show());
    }
}
