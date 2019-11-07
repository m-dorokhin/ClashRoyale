package com.example.clashroyale.activities.card;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.clashroyale.models.CardView;
import com.example.clashroyale.repositories.Repository;

import java.util.ArrayList;
import java.util.List;

public class CardViewModel extends ViewModel {
    private final Repository mRepository;

    public final ObservableField<List<CardView>> cards = new ObservableField<>(new ArrayList<>());

    public CardViewModel(Repository repository) {
        mRepository = repository;

        cards.set(mRepository.getDeck());
    }
}
