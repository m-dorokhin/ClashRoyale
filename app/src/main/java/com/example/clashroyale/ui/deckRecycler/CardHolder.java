package com.example.clashroyale.ui.deckRecycler;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;
import com.example.clashroyale.databinding.MiniatureCardLayoutBinding;
import com.example.clashroyale.models.CardView;

public class CardHolder extends RecyclerView.ViewHolder {
    private final MiniatureCardLayoutBinding mBinding;
    // Показывает что сейчас проигрывается анимация, используется для анимации простоя
    private boolean mIsAnimationStarted = false;

    public CardHolder(@NonNull MiniatureCardLayoutBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(CardView card) {
        mBinding.setCard(card);
        mBinding.executePendingBindings();
    }

    public void setOnClickItemListener(View.OnClickListener listener) {
        mBinding.getRoot().setOnClickListener(listener);
    }

    public void startCollectCardsAnimation() {
        mIsAnimationStarted = true;
        int position = this.getAdapterPosition();
        Animation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 3
        );
        animation.setDuration(1000);
        animation.setStartOffset(position * 50);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimationStarted = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        mBinding.getRoot().startAnimation(animation);
    }

    public void startDealCardsAnimation(int position) {
        mIsAnimationStarted = true;
        ImageView cardShirt = mBinding.getRoot().findViewById(R.id.card_shirt);
        cardShirt.setVisibility(View.VISIBLE);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(getDistributionCardAnimation(position));
        animationSet.addAnimation(getTurnCardPart1Animation());
        animationSet.addAnimation(getTurnCardPart2Animation());
        animationSet.setStartOffset(position * 50);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimationStarted = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        mBinding.getRoot().startAnimation(animationSet);
    }

    private Animation getDistributionCardAnimation(int position) {
        float posX = position % 4;
        float posY = position > 3 ? 1 : 0;
        // 168 = mBinding.getRoot().getWidth();
        float fromXDelta = (float)(-1*posX-1)*168;
        // 215 = mBinding.getRoot().getHeight();
        float fromYDelta = (float)(-1*posY-1)*215;

        Animation animation = new TranslateAnimation(
                fromXDelta,
                0,
                fromYDelta,
                0);
        animation.setDuration(400);
        return animation;
    }

    private Animation getTurnCardPart1Animation() {
        Animation animation = new ScaleAnimation(
                1.0f, 0.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillEnabled(true);
        animation.setFillAfter(false);
        animation.setDuration(250);
        animation.setStartOffset(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageView cardShirt = mBinding.getRoot().findViewById(R.id.card_shirt);
                cardShirt.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    private Animation getTurnCardPart2Animation() {
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillEnabled(true);
        animation.setFillBefore(false);
        animation.setDuration(250);
        animation.setStartOffset(650);
        return animation;
    }

    public void startIdleAnimation() {
        if (mIsAnimationStarted)
            return;

        Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f);
        animation.setFillEnabled(true);
        animation.setFillAfter(false);
        animation.setDuration(100);

        Animation animation2 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f);
        animation2.setFillEnabled(true);
        animation2.setFillBefore(false);
        animation2.setStartOffset(100);
        animation2.setDuration(100);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(animation2);
        animationSet.setStartOffset(this.getAdapterPosition() * 100);

        mBinding.getRoot().startAnimation(animationSet);
    }
}
