package com.example.clashroyale.ui.deckRecycler;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clashroyale.models.CardView;

import java.util.Date;
import java.util.List;

public class DeckRecycler extends RecyclerView {
    private final static int COLUMN_COUNT = 4;
    private final static int IDLE_TIMEOUT = 5000;
    private Date mLastUpdateDate = new Date();
    private final Runnable mIdleAnimationStarter = new Runnable() {
        @Override
        public void run() {
            Log.i("DeckRecycler", "IDLE started: " + mLastUpdateDate);
            Adapter adapter = getAdapter();
            int itemCount = adapter.getItemCount();
            if (itemCount > 0 &&
                    // Проверка времени последнего обнавления нужна что бы анимация простоя
                    // не накладывалась на анимацию получения карт.
                    // Возможно это всё ещё может произойти, но при получении данных дольше IDLE_TIMEOUT
                    // и запуски обеих анимаций одновременно.
                    // Но это надо ещё поймать ;)
                    (new Date().getTime() - mLastUpdateDate.getTime()) > IDLE_TIMEOUT ) {
                Log.i("DeckRecycler", "IDLE animation started");
                adapter.notifyItemRangeChanged(0, itemCount, DeckAdapter.IDLE_CHANGE);
            }

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

    public void setItems(List<CardView> items) {
        mLastUpdateDate = new Date();
        DeckAdapter adapter = getAdapter();
        if (items.isEmpty()) {
            adapter.removeItems();
        } else {
            if (adapter.getItemCount() > 0)
                adapter.removeItems();

            adapter.insertItems(items);
        }
    }
}
