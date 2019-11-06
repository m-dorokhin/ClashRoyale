package com.example.clashroyale.ui.cardPager;

import androidx.databinding.BindingAdapter;

import com.example.clashroyale.api.models.Card;

import java.util.List;

public class CardBindingAdapters {
    @BindingAdapter({"app:items"})
    public static void setItems(CardPager view, List<Card> items) {
        view.getAdapter().setItems(items);
    }
}
