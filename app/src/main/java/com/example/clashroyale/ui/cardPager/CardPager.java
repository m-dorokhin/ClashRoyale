package com.example.clashroyale.ui.cardPager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.clashroyale.api.models.Card;

import java.util.List;

public class CardPager extends ViewPager {
    public CardPager(@NonNull Context context) {
        super(context);
        this.setAdapter(new CardAdapter(context));
    }

    public CardPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setAdapter(new CardAdapter(context));
    }

    @Nullable
    @Override
    public CardAdapter getAdapter() {
        return (CardAdapter) super.getAdapter();
    }

    public void setItems(List<Card> items) {
        this.getAdapter().setItems(items);
    }
}
