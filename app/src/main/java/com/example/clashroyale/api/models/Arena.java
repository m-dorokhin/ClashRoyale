package com.example.clashroyale.api.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class Arena {
    public static class Clan {
        public ClanDetail donate;
        public ClanDetail request;
    }

    public static class ClanDetail {
        public int common;
        public int rare;
    }

    @JsonAlias("_id")
    public String id;
    public String idName;
    public int number;
    public String name;
    public int victoryGold;
    public int minTrophies;
    public int order;
    @JsonAlias("__v")
    public int v;
    public List<String> leagues;
    public List<String> cardUnlocks;
    public List<String> chests;
    public Clan clan;
}
