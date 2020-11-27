package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.model.ModelControllerTemperature;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

public class TemperatureActivity extends AppCompatActivity {
    SensorData values;
    GraphView graph;
    ModelControllerTemperature controller;
    ImageButton upMaxBtn;
    ImageButton downMaxBtn;
    ImageButton upMinBtn;
    ImageButton downMinBtn;
    private TextView maxTxt;
    private TextView minTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Cache le titre de l'application
        getSupportActionBar().hide(); //cache l'espace du titre
        setContentView(R.layout.activity_temperature);

        maxTxt = findViewById(R.id.maxTempNb);
        minTxt = findViewById(R.id.minTempNb);
        graph = (GraphView) findViewById(R.id.graph);
        upMaxBtn = findViewById(R.id.upMaxTemp);
        downMaxBtn = findViewById(R.id.downMaxTemp);
        upMinBtn = findViewById(R.id.upMinTemp);
        downMinBtn = findViewById(R.id.downMinTemp);

        if (savedInstanceState != null) {
            values = savedInstanceState.getParcelable("SI_PARCEL_SENSOR");
        }

        //Recherche de l'intent explicite et implicite
        Intent intent = getIntent();
        if (intent.hasExtra("SENSOR_PARCEL")) {
            values = intent.getParcelableExtra("SENSOR_PARCEL");
        }

        controller = new ModelControllerTemperature(values);
        updateTextCount();
        addGraphData();
    }

    private void addGraphData(){
        DataPoint[] data = new DataPoint[values.getValues().length];
        int i = 0;
        for(SensorValue sensorValues: values.getValues()){
            data[i] = new DataPoint(sensorValues.getTimeStamp(), sensorValues.getValue());
            i++;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        graph.addSeries(series);
        graph.getLegendRenderer().setVisible(true);
    }

    private void updateTextCount(){
        maxTxt.setText(Double.toString(controller.getHighLimit()));
        minTxt.setText(Double.toString(controller.getLowLimit()));
    }

    public void onUpMaxButton(View view) {
        controller.onHighLimitUp();
        updateTextCount();
    }

    public void onUpMinButton(View view) {
        controller.onLowLimitUp();
        updateTextCount();
    }

    public void onDownMaxButton(View view) {
        controller.onHighLimitDown();
        updateTextCount();
    }

    public void onDownMinButton(View view) {
        controller.onLowLimitDown();
        updateTextCount();
    }
}