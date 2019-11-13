package com.example.clashroyale.services;

import android.util.Log;

import com.example.clashroyale.models.CardView;
import com.example.clashroyale.utilits.ActionT;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageLoader {
    private int mCount = 0;

    public void load(List<CardView> cardViews, ActionT<List<CardView>> callback) {
        mCount = cardViews.size();
        Log.i("ImageLoader", "Image counts: " + mCount);

        for (CardView card: cardViews) {
            String url = card.getImageUrl();
            Picasso.get().load(url).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    mCount -= 1;
                    Log.i("ImageLoader", "Remained images: " + mCount);

                    if(mCount <= 0)
                        callback.execute(cardViews);
                }

                @Override
                public void onError(Exception e) {
                    mCount -= 1;
                    Log.i("ImageLoader", "Remained images: " + mCount);
                    Log.e("ImageLoader", "Can't load image from url: " + url, e);

                    if(mCount <= 0)
                        callback.execute(cardViews);
                }
            });
        }
    }
}
