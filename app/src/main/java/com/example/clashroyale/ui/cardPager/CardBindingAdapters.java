package com.example.clashroyale.ui.cardPager;

import androidx.databinding.BindingAdapter;

import com.example.clashroyale.models.CardView;

import java.util.List;

public class CardBindingAdapters {
    @BindingAdapter({"app:items"})
    public static void setItems(CardPager view, List<CardView> items) {
        view.getAdapter().setItems(items);
    }
}
