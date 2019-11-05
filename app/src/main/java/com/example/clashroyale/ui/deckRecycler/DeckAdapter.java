package com.example.clashroyale.ui.deckRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.databinding.MiniatureCardLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private List<Card> mItems;
    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(@Nullable OnClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
        notifyDataSetChanged();
    }

    public void setItems(List<Card> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    public DeckAdapter() {
        mItems = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MiniatureCardLayoutBinding mBinding;

        public ViewHolder(@NonNull MiniatureCardLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Card card) {
            mBinding.setCard(card);
            mBinding.executePendingBindings();
        }

        public void setOnClickItemListener(View.OnClickListener listener) {
            mBinding.getRoot().setOnClickListener(listener);
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
            holder.setOnClickItemListener((view) -> mOnClickItemListener.onClickItem(view, position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
