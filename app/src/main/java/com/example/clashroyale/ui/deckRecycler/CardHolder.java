package com.example.clashroyale.ui.deckRecycler;

import android.view.View;
import android.view.animation.Animation;
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

    public void startDealCardsAnimation(int position) {
        mIsAnimationStarted = true;
        ImageView cardShirt = mBinding.getRoot().findViewById(R.id.card_shirt);
        cardShirt.setVisibility(View.VISIBLE);

        Animation animation = DeckAnimationsHelper
                .getDealCardsAnimation(position, () -> cardShirt.setVisibility(View.INVISIBLE));
        animation.setStartOffset(position * 50);
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

    public void startIdleAnimation() {
        if (mIsAnimationStarted)
            return;

        Animation animation = DeckAnimationsHelper.getIdleAnimation();
        animation.setStartOffset(this.getAdapterPosition() * 100);

        mBinding.getRoot().startAnimation(animation);
    }

    public void startCollectCardsAnimation() {
        mIsAnimationStarted = true;
        int position = this.getAdapterPosition();
        Animation animation = DeckAnimationsHelper.getCollectCardsAnimation();
        animation.setStartOffset(position * 50);
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
}
