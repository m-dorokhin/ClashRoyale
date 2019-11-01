package com.example.clashroyale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clashroyale.application.App;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        App.getComponent().InjectsCardActivity(this);
    }
}
