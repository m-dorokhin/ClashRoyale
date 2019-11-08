package com.example.clashroyale.activities.deck;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.clashroyale.R;
import com.example.clashroyale.activities.card.CardActivity;
import com.example.clashroyale.models.CardView;
import com.example.clashroyale.services.NetStatusReceiver;
import com.example.clashroyale.services.Repository;
import com.example.clashroyale.utilits.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final NetStatusReceiver mNetStatusReceiver;

    public final ObservableField<List<CardView>> cards = new ObservableField<>(new ArrayList<>());
    public final ObservableBoolean requested = new ObservableBoolean(false);
    public final ObservableBoolean netAvailable = new ObservableBoolean(true);
    public final ObservableDouble averageElixir = new ObservableDouble(0);

    private final SingleLiveEvent<String> mSnackMessage = new SingleLiveEvent<>();

    public LiveData<String> getSnackMessage() {
        return mSnackMessage;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DeckViewModel(
            @NonNull Application application,
            @NonNull Repository repository,
            @NonNull NetStatusReceiver netStatusReceiver) {
        super(application);
        mRepository = repository;
        mNetStatusReceiver = netStatusReceiver;

        mNetStatusReceiver.setOnNetEnable(() -> {
            if (!netAvailable.get()) {
                mSnackMessage.setValue(getApplication()
                        .getString(R.string.network_is_available));
            }
            netAvailable.set(true);
        });
        mNetStatusReceiver.setOnNetDisable(() -> {
            if (netAvailable.get()) {
                mSnackMessage.setValue(getApplication()
                        .getString(R.string.network_is_not_available));
            }
            netAvailable.set(false);
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        application.registerReceiver(mNetStatusReceiver, filter);

        List<CardView> cachedCards = mRepository.getDeck();
        cards.set(cachedCards);
        if (cachedCards.size() > 0) {
            averageElixir.set(cachedCards.stream()
                    .mapToInt((card) -> card.elixirCost)
                    .average()
                    .getAsDouble());
        } else {
            requestDeck();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getApplication().unregisterReceiver(mNetStatusReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestDeck() {
        if (requested.get() || !netAvailable.get())
            return;

        requested.set(true);
        cards.set(new ArrayList<>());
        averageElixir.set(0);

        mRepository.newDeck(
                (receivedCards) -> {
                    cards.set(receivedCards);
                    requested.set(false);
                    averageElixir.set(receivedCards.stream()
                            .mapToInt((card) -> card.elixirCost)
                            .average()
                            .getAsDouble());
                },
                () -> {
                    mSnackMessage.setValue(getApplication()
                            .getString(R.string.network_is_not_available));
                    requested.set(false);
                });
    }

    public void gotoCard(int cardNo) {
        Log.i("DeckViewModel", "Selected card: " + cardNo);
        Context context = getApplication();
        Intent intent = new Intent(context, CardActivity.class);
        intent.putExtra(CardActivity.EXTRA_CARD_NO, cardNo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Без этого флага не запускает
        context.startActivity(intent);
    }
}
