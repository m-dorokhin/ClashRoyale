package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeckRecycler extends RecyclerView {
    private final int COLUMN_COUNT = 4;

    public DeckRecycler(@NonNull Context context) {
        super(context);

        this.setAdapter(new DeckAdapter());
        GridLayoutManager layoutManager = new GridLayoutManager(context, COLUMN_COUNT);
        this.setLayoutManager(layoutManager);
    }

    public DeckRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setAdapter(new DeckAdapter());
        GridLayoutManager layoutManager = new GridLayoutManager(context, COLUMN_COUNT);
        this.setLayoutManager(layoutManager);
    }

    public DeckRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.setAdapter(new DeckAdapter());
        GridLayoutManager layoutManager = new GridLayoutManager(context, COLUMN_COUNT);
        this.setLayoutManager(layoutManager);
    }

    @Nullable
    @Override
    public DeckAdapter getAdapter() {
        return (DeckAdapter) super.getAdapter();
    }
}
