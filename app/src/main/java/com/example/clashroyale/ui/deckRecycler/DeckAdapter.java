package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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

public class DeckAdapter extends RecyclerView.Adapter<CardHolder> implements CardTouchHelperAdapter{
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
                new Handler().postDelayed(mRunnable, 5000);
            }
        };
        new Handler().postDelayed(mRunnable, 5000);
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MiniatureCardLayoutBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.miniature_card_layout, parent, false);
        return new CardHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
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
