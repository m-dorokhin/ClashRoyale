package com.example.clashroyale.activities.card;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.clashroyale.R;
import com.example.clashroyale.activities.ViewModelFactory;
import com.example.clashroyale.application.App;
import com.example.clashroyale.databinding.ActivityCardBinding;

import javax.inject.Inject;

public class CardActivity extends AppCompatActivity {

    @Inject
    public ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getComponent().InjectsCardActivity(this);

        CardViewModel cardViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(CardViewModel.class);
        ActivityCardBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_card);
        binding.setCards(cardViewModel);
    }
}
