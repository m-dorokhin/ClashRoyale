package com.example.clashroyale.models;

import android.graphics.Bitmap;

import com.example.clashroyale.api.models.Card;

public class CardView {
    public String idName;
    public String rarity;
    public String type;
    public String name;
    public String description;
    public int elixirCost;
    public Bitmap image;

    public CardView() { }

    public CardView(Card card) {
        this.idName = card.idName;
        this.rarity = card.rarity;
        this.type = card.type;
        this.name = card.name;
        this.description = card.description;
        this.elixirCost = card.elixirCost;
    }
}
