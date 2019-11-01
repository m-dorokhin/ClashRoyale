package com.example.clashroyale.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.utilits.Action;
import com.example.clashroyale.utilits.ActionT;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {
    private final ClashRoyaleApi mClashRoyaleApi;

    public Api(@NonNull ClashRoyaleApi clashRoyaleApi) {
        this.mClashRoyaleApi = clashRoyaleApi;
    }

    public void RandomDeck(@NonNull final ActionT<List<Card>> callback, @NonNull final Action error) {
        Log.i("Api", "Request random deck");
        mClashRoyaleApi.randomDeck().enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.i("Api", "Request random deck done");
                callback.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.e("Api", "Request random deck failed", t);
                error.execute();
            }
        });
    }
}
