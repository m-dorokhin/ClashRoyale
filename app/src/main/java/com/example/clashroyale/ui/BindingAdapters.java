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

    @BindingAdapter({"app:set_background_resource"})
    public static void setBackgroundResource(RelativeLayout view, int resourceId) {
        view.setBackgroundResource(resourceId);
    }
}
