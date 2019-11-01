package com.example.clashroyale.activities.deck;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;
import com.example.clashroyale.activities.ViewModelFactory;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.application.App;
import com.example.clashroyale.databinding.ActivityDeckBinding;

import java.util.ArrayList;
import java.util.List;

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

        RecyclerView deckRecycler = findViewById(R.id.deck_recycler);
        final DeckAdapter adapter = new DeckAdapter();
        deckRecycler.setAdapter(adapter);
        deckViewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> dayWeathers) {
                adapter.setItems(dayWeathers);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        deckRecycler.setLayoutManager(layoutManager);
    }
}
