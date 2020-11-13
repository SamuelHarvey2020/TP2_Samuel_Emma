package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.w32.tp2_samuel_emma.R;

public class WeatherActivity extends AppCompatActivity {
    //TODO: Sérialisation des parcelable (avec les données des capteurs)
    //TODO: Affichage du nombre de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    public void onGoBack(View view) {
        onBackPressed();
    }

}