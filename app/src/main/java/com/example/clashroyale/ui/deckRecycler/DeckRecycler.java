package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DeckRecycler extends RecyclerView {
    private final static int COLUMN_COUNT = 4;
    private final static int IDLE_TIMEOUT = 5000;
    private final Runnable mIdleAnimationStarter = new Runnable() {
        @Override
        public void run() {
            Adapter adapter = getAdapter();
            int itemCount = adapter.getItemCount();
            if (itemCount > 0)
                adapter.notifyItemRangeChanged(0, itemCount, DeckAdapter.IDLE_CHANGE);

            new Handler().postDelayed(mIdleAnimationStarter, IDLE_TIMEOUT);
        }
    };

    public DeckRecycler(@NonNull Context context) {
        super(context);

        initilize(context);
    }

    public DeckRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initilize(context);
    }

    public DeckRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initilize(context);
    }

    private void initilize(Context context) {
        this.setAdapter(new DeckAdapter(context));
        GridLayoutManager layoutManager = new GridLayoutManager(context, COLUMN_COUNT);
        this.setLayoutManager(layoutManager);

        // Обработка drag and drop
        ItemTouchHelper.Callback cardTouchCallback = new CardTouchCallback(this.getAdapter());
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardTouchCallback);
        touchHelper.attachToRecyclerView(this);

        this.setItemAnimator(new DeckAnimator());
        // Запускаем обработку анимации простоя
        new Handler().postDelayed(mIdleAnimationStarter, IDLE_TIMEOUT);
    }

    @Nullable
    @Override
    public DeckAdapter getAdapter() {
        return (DeckAdapter) super.getAdapter();
    }

    public void setOnClickItemListener(@Nullable OnClickItemListener listener) {
        this.getAdapter().setOnClickItemListener(listener);
    }
}
