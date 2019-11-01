package com.example.clashroyale.api;

import com.example.clashroyale.api.models.Card;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClashRoyaleApi {
    @GET("api/random-deck")
    Call<List<Card>> randomDeck();
}
