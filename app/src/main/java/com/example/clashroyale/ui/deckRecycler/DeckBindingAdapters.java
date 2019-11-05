package com.example.clashroyale.ui.deckRecycler;

import androidx.databinding.BindingAdapter;

import com.example.clashroyale.api.models.Card;

import java.util.List;

public class DeckBindingAdapters {
    @BindingAdapter({"app:items"})
    public static void setItems(DeckRecycler view, List<Card> items) {
        view.getAdapter().setItems(items);
    }

    @BindingAdapter({"app:onClickItem"})
    public static void setOnClickItemListener(DeckRecycler view, OnClickItemListener listener) {
        view.getAdapter().setOnClickItemListener(listener);
    }
}
