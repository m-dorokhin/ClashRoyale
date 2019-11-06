package com.example.clashroyale.activities.card;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.clashroyale.R;
import com.example.clashroyale.activities.ViewModelFactory;
import com.example.clashroyale.application.App;
import com.example.clashroyale.databinding.ActivityCardBinding;
import com.example.clashroyale.ui.cardPager.CardPager;

import javax.inject.Inject;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_NO = "extra_card_no";
    @Inject
    public ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getComponent().InjectsCardActivity(this);

        CardViewModel cardViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(CardViewModel.class);
        ActivityCardBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_card);
        binding.setCards(cardViewModel);

        Intent intent = getIntent();
        int cardNo = intent.getIntExtra(this.EXTRA_CARD_NO, 0);
        CardPager cardPager = findViewById(R.id.card_pager);

        // Так как сразу после загрухки карты ещё не подгрузились, подождём немного,
        // а потом установим нужную карту
        // TODO: Подчинить установку текущей карты в компаненте CardPager
        new Handler().postDelayed(() -> {
            Log.i("CardActivity", "CardActivity started with card no " + cardNo
                    + " cards count " + cardPager.getAdapter().getCount());
            cardPager.setCurrentItem(cardNo, false);
        }, 100);
    }
}
