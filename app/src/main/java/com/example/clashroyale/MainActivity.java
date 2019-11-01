package com.example.clashroyale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clashroyale.application.App;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().InjectsMainActivity(this);
    }
}
