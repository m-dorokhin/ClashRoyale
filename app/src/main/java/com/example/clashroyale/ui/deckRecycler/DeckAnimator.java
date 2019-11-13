package com.example.clashroyale.ui.deckRecycler;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;

import java.util.List;

public class DeckAnimator extends DefaultItemAnimator {
    private static final String TAG = DeckAnimator.class.getSimpleName();

    public static class CardItemHolderInfo extends ItemHolderInfo {
        public String updateAction;

        public CardItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    public ItemHolderInfo recordPreLayoutInformation(
            @NonNull RecyclerView.State state,
            @NonNull RecyclerView.ViewHolder viewHolder,
            int changeFlags,
            @NonNull List<Object> payloads) {
        switch (changeFlags) {
            case FLAG_APPEARED_IN_PRE_LAYOUT:
                Log.i(TAG, "recordPreLayoutInformation FLAG_APPEARED_IN_PRE_LAYOUT");
                break;
            case FLAG_CHANGED:
                Log.i(TAG, "recordPreLayoutInformaiton FLAG_CHANGED");
                for (Object payload: payloads){
                    if (payload instanceof String) {
                        Log.i(TAG, "payload: " + (String) payload);
                        return new CardItemHolderInfo((String) payload);
                    }
                }
                break;
            case FLAG_INVALIDATED:
                Log.i(TAG, "recordPreLayoutInformation FLAG_INVALIDATED");
                break;
            case FLAG_MOVED:
                Log.i(TAG, "recordPreLayoutInformation FLAG_MOVED");
                break;
            case FLAG_REMOVED:
                Log.i(TAG, "recordPreLayoutInformation FLAG_REMOVED");
                break;

        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        Log.i(TAG, "animateAdd");

        int position = holder.getAdapterPosition();
        ImageView cardShirt = holder.itemView.findViewById(R.id.card_shirt);
        cardShirt.setVisibility(View.VISIBLE);

        Animation animation = DeckAnimationsHelper
                .getDealCardsAnimation(() -> cardShirt.setVisibility(View.INVISIBLE));
        animation.setStartOffset(position * 50);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardShirt.setVisibility(View.INVISIBLE);
                DeckAnimator.this.dispatchAddFinished(holder);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        holder.itemView.startAnimation(animation);
        return true;
    }

    @Override
    public boolean animateChange(
            @NonNull RecyclerView.ViewHolder oldHolder,
            @NonNull RecyclerView.ViewHolder newHolder,
            @NonNull ItemHolderInfo preInfo,
            @NonNull ItemHolderInfo postInfo) {
        Log.i(TAG, "animateChange, is running: " + this.isRunning());
        if (preInfo instanceof CardItemHolderInfo) {
            CardItemHolderInfo cardItemHolderInfo = (CardItemHolderInfo) preInfo;
            CardHolder holder = (CardHolder) newHolder;

            if (DeckAdapter.IDLE_CHANGE.equals(cardItemHolderInfo.updateAction) && !this.isRunning()) {
                int position = holder.getAdapterPosition();
                Animation animation = DeckAnimationsHelper.getIdleAnimation();
                animation.setStartOffset(position * 150);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dispatchAnimationFinished(oldHolder);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });

                holder.itemView.startAnimation(animation);
                return true;
            }
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }

    @Override
    public boolean animateMove(
            RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        Log.i(TAG, "animateMove");
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        Log.i(TAG, "animateRemove");

        // Делаем карты не кликабильными
        holder.itemView.setOnClickListener(null);

        int parentWidth = ((View)holder.itemView.getParent()).getWidth();
        int width = holder.itemView.getWidth();
        int height = holder.itemView.getHeight();
        int x = (int) Math.floor((double) holder.itemView.getX());
        int y = (int) Math.floor((double) holder.itemView.getY());

        int position = y / height * parentWidth / width + x / width;
        Animation animation = DeckAnimationsHelper.getCollectCardsAnimation();
        animation.setStartOffset(position * 50);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                DeckAnimator.this.dispatchRemoveFinished(holder);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        holder.itemView.startAnimation(animation);
        return true;
    }
}
