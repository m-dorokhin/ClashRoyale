package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private final Context mContext;
    private List<CardView> mItems;
    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(@Nullable OnClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
        notifyDataSetChanged();
    }

    public void setItems(List<CardView> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    public DeckAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MiniatureCardLayoutBinding mBinding;

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

        public void startAnimation(int position) {
            ImageView cardShirt = mBinding.getRoot().findViewById(R.id.card_shirt);
            cardShirt.setVisibility(View.VISIBLE);

            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(getDistributionCardAnimation(position));
            animationSet.addAnimation(getTurnCardPart1Animation());
            animationSet.addAnimation(getTurnCardPart2Animation());
            animationSet.setStartOffset(position * 50);

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
                    (view) -> mOnClickItemListener.onClickItem(view, position));

        holder.startAnimation(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
