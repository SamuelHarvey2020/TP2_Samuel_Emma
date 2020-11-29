package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.model.ModelControllerHumidity;
import com.w32.tp2_samuel_emma.model.ModelControllerTemperature;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

public class HumidityActivity extends AppCompatActivity {

    SensorData values;
    SensorValue[] valuesArray;
    private TextView textView;
    ModelControllerHumidity controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        textView = findViewById(R.id.weatherInfoTxt);

        if (savedInstanceState != null) {
            values = savedInstanceState.getParcelable("SI_PARCEL_SENSOR");
            textView.setText(savedInstanceState.getString("SI_TEXT"));
        }

        //Recherche de l'intent explicite et implicite
        Intent intent = getIntent();
        if (intent.hasExtra("SENSOR_PARCEL")) {
            values = intent.getParcelableExtra("SENSOR_PARCEL");
        }

        valuesArray = values.getValues();
        showDataCount(valuesArray.length);

        controller = new ModelControllerHumidity(values);
        GraphView graph = (GraphView) findViewById(R.id.graphHumidity);
        BarGraphSeries<DataPoint> series = ModelControllerHumidity.getSeries();
        graph.addSeries(series);
        series.setSpacing(50);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("SI_PARCEL_SENSOR", this.values);
        outState.putString("SI_TEXT", this.textView.getText().toString());
    }

    public void onGoBack(View view) {
        onBackPressed();
    }

    /***
     * Affiche dans le TextView le nombre de données reçues
     * @param count : nombre de données
     */
    private void showDataCount(int count){
        String output = count + " " + getApplicationContext().getString(R.string.data_read);
        this.textView.setText(output);
    }
}