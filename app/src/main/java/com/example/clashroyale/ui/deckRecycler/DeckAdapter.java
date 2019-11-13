package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<CardHolder> implements CardTouchHelperAdapter {
    public final static String IDLE_CHANGE = "idle_change";
    private List<CardView> mItems;
    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(@Nullable OnClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
    }

    public void setItems(List<CardView> items) {
        // Если пришёл пустой массив карт то удоляем содержимое
        if (items.isEmpty()) {
            int count = this.mItems.size();
            this.mItems = items;
            notifyItemRangeRemoved(0, count);
        } else { // Иначи заполняем
            this.mItems = items;
            notifyItemRangeInserted(0, items.size());
        }
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
        mItems = new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
