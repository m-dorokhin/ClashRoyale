package com.example.clashroyale.ui.cardFlipper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import androidx.databinding.DataBindingUtil;

import com.example.clashroyale.R;
import com.example.clashroyale.databinding.CardLayoutBinding;
import com.example.clashroyale.models.CardView;

import java.util.LinkedList;
import java.util.List;

public class CardFlipper extends ViewFlipper {
    private float fromPosition;
    private List<CardView> mItems;
    private List<CardLayoutBinding> mBindings;

    @SuppressLint("ClickableViewAccessibility")
    public CardFlipper(Context context) {
        super(context);
        this.setOnTouchListener(this::onTouch);
    }

    @SuppressLint("ClickableViewAccessibility")
    public CardFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this::onTouch);
    }

    // TODO: Переделать пересоздание биндингов на их переиспользование, возможно вынести эту логику в отдельный класс
    public void setItems(List<CardView> items) {
        this.removeAllViews();

        mItems = items;
        mBindings = new LinkedList<>();
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        for(CardView item: items) {
            CardLayoutBinding binding = DataBindingUtil
                    .inflate(inflater, R.layout.card_layout, this, false);
            binding.setCard(item);
            binding.executePendingBindings();
            mBindings.add(binding);
            this.addView(binding.getRoot());
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fromPosition = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (fromPosition > toPosition) {
                    this.setInAnimation(AnimationUtils
                            .loadAnimation(this.getContext(), R.anim.flip_in_right_to_left));
                    this.setOutAnimation(AnimationUtils
                            .loadAnimation(this.getContext(), R.anim.flip_out_right_to_left));
                    this.showNext();
                } else if (fromPosition < toPosition) {
                    this.setInAnimation(AnimationUtils
                            .loadAnimation(this.getContext(), R.anim.flip_in_left_to_right));
                    this.setOutAnimation(AnimationUtils
                            .loadAnimation(this.getContext(), R.anim.flip_out_left_to_right));
                    this.showPrevious();
                }
            default:
                break;
        }
        return true;
    }
}
