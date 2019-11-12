package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;
import com.example.clashroyale.databinding.MiniatureCardLayoutBinding;
import com.example.clashroyale.models.CardView;
import com.example.clashroyale.utilits.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> implements CardTouchHelperAdapter{
    private final Context mContext;
    private List<CardView> mItems;
    private OnClickItemListener mOnClickItemListener;
    private final LinkedList<Action> mCollectCardsAnimations = new LinkedList<>();
    private final LinkedList<Action> mIdleAnimations = new LinkedList<>();
    private final Runnable mRunnable;

    public void setOnClickItemListener(@Nullable OnClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
    }

    // Переменная в true указывает что анимация сбора карт запущена, а новая колода ещё на добавлена
    // И надо будет произвести отчистку RecycleView
    // В false отчистку RecycleView проводить не надо, так как уже добавлена новая колода
    private boolean clear = false;
    public void setItems(List<CardView> items) {
        // Если нет карт на экрани, то просто отобразим новую колоду
        if (mCollectCardsAnimations.size() <= 0) {
            this.mItems = items;
            notifyDataSetChanged();
            clear = false;
            return;
        }

        // Если есть то покажим анимацию сбора карт
        for(Action animation: mCollectCardsAnimations) {
            animation.execute();
        }
        // Почистим анимации сбора карт и простоя
        mCollectCardsAnimations.clear();
        mIdleAnimations.clear();

        clear = true;
        // Запустим отчистку RecyclerView с задержкой в 1 сек,
        // что бы успела отыграть анимация сбора карт
        new Handler().postDelayed(() -> {
            if (clear) {
                this.mItems = items;
                notifyDataSetChanged();
            }
        }, 1000);
    }

    public void onItemDismiss(int position) {
        Log.i("DeckAdapter", "item dismiss on position: " + position);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public DeckAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!mIdleAnimations.isEmpty()) {
                    for (Action animation: mIdleAnimations) {
                        animation.execute();
                    }
                }
                Handler handler;
                new Handler().postDelayed(mRunnable, 5000);
            }
        };
        new Handler().postDelayed(mRunnable, 5000);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MiniatureCardLayoutBinding mBinding;
        // Показывает что сейчас проигрывается анимация, используется для анимации простоя
        private boolean mIsAnimationStarted = false;

        public ViewHolder(@NonNull MiniatureCardLayoutBinding binding) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MiniatureCardLayoutBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.miniature_card_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mItems.get(position));

        if (mOnClickItemListener != null)
            holder.setOnClickItemListener(
                    (view) -> mOnClickItemListener.onClickItem(view, holder.getAdapterPosition()));

        holder.startDealCardsAnimation(position);
        mCollectCardsAnimations.add(holder::startCollectCardsAnimation);
        mIdleAnimations.add(holder::startIdleAnimation);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
