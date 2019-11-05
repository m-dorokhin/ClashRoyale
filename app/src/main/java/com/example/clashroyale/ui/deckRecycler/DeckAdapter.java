package com.example.clashroyale.ui.deckRecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.R;
import com.example.clashroyale.api.models.Card;
import com.example.clashroyale.databinding.CardLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private List<Card> mItems;

    public void setItems(List<Card> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    public DeckAdapter() {
        mItems = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardLayoutBinding mBinding;

        public ViewHolder(@NonNull CardLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Card card) {
            mBinding.setCard(card);
            mBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardLayoutBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.card_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
