package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

public class WeatherActivity extends AppCompatActivity {
    //TODO: Affichage du nombre de données

    SensorData values;
    SensorValue[] valuesArray;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        if (intent.hasExtra("SENSOR_PARCEL")) {
            values = intent.getParcelableExtra("SENSOR_PARCEL");
        }

        valuesArray = values.getValues();
        showDataCount(countValues());
    }

    public void onGoBack(View view) {
        onBackPressed();
    }

    private int countValues(){
        int count = 0;
        while (count < valuesArray.length){
            count++;
        }
        return count;
    }

    private void showDataCount(int count){
        String output = count + " données ont été enregistrées.";
        textView.setText(output);
    }
}