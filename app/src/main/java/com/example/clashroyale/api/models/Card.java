package com.example.clashroyale.api.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {
    @JsonAlias("_id")
    public String id;
    public String idName;
    public String rarity;
    public String type;
    public String name;
    public String description;
    public int elixirCost;
    public int copyId;
    public int arena;
    public int order;
    @JsonAlias("__v")
    public int v;
}
