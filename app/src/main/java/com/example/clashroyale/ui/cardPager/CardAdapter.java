package com.example.clashroyale.ui.cardPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.clashroyale.R;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.databinding.CardLayoutBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CardAdapter extends PagerAdapter {
    private final Context mContext;
    private List<Card> mItems = new ArrayList<>();
    private final List<CardLayoutBinding> mBindings = new LinkedList<>();

    public void setItems(List<Card> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public CardAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardLayoutBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.card_layout, container, false);
        binding.setCard(mItems.get(position));
        binding.executePendingBindings();

        mBindings.add(binding);
        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

        for (CardLayoutBinding binding: mBindings) {
            if (binding.getRoot() == object) {
                mBindings.remove(binding);
                break;
            }
        }
    }
}
