package com.example.clashroyale.ui.deckRecycler;

import androidx.databinding.BindingAdapter;

import com.example.clashroyale.models.CardView;

import java.util.List;

public class DeckBindingAdapters {
    @BindingAdapter({"app:items"})
    public static void setItems(DeckRecycler view, List<CardView> items) {
        view.setItems(items);
    }

    @BindingAdapter({"app:onClickItem"})
    public static void setOnClickItemListener(DeckRecycler view, OnClickItemListener listener) {
        view.setOnClickItemListener(listener);
    }
}
