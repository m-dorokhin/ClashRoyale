package com.example.clashroyale.ui.deckRecycler;

public interface CardTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
