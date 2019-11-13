package com.example.clashroyale.ui.deckRecycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.databinding.MiniatureCardLayoutBinding;
import com.example.clashroyale.models.CardView;

public class CardHolder extends RecyclerView.ViewHolder {
    private final MiniatureCardLayoutBinding mBinding;

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
}
