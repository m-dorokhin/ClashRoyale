package com.example.clashroyale.ui;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    @BindingAdapter({"app:set_background_color"})
    public static void setColor(RelativeLayout view, int colorResourceId) {
        view.setBackgroundResource(colorResourceId);
    }
}
