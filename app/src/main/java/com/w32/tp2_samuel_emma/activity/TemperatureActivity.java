package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.controller.ModelControllerTemperature;
import com.w32.tp2_samuel_emma.data.SensorDataStats;
import com.w32.tp2_samuel_emma.database.MyDatabaseFactory;
import com.w32.tp2_samuel_emma.repository.SensorDataRepository;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorID;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

import java.util.Calendar;

public class TemperatureActivity extends AppCompatActivity {
    SensorData values;
    GraphView graph;
    ModelControllerTemperature controller;
    //Interface
    ImageButton upMaxBtn;
    ImageButton downMaxBtn;
    ImageButton upMinBtn;
    ImageButton downMinBtn;
    private TextView maxTxt;
    private TextView minTxt;
    //Graphique
    private LineGraphSeries<DataPoint> maxSeries;
    private LineGraphSeries<DataPoint> minSeries;
    //Database
    private MyDatabaseFactory databaseFactory;
    private SensorDataRepository repoSensorData;
    private SQLiteDatabase database;
    private SensorDataStats sensorDataStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Cache le titre de l'application
        getSupportActionBar().hide(); //cache l'espace du titre
        setContentView(R.layout.activity_temperature);

        //===========CONNEXION A LA BD POUR L'INTERFACE=========

        databaseFactory = new MyDatabaseFactory(this);
        database = databaseFactory.getWritableDatabase();
        sensorDataStats = new SensorDataStats();

        //Obtention d'un repository pour l'accès aux données
        repoSensorData = new SensorDataRepository(database);
        //=======================================================

        maxTxt = findViewById(R.id.maxTempNb);
        minTxt = findViewById(R.id.minTempNb);
        graph = (GraphView) findViewById(R.id.graph);
        upMaxBtn = findViewById(R.id.upMaxTemp);
        downMaxBtn = findViewById(R.id.downMaxTemp);
        upMinBtn = findViewById(R.id.upMinTemp);
        downMinBtn = findViewById(R.id.downMinTemp);

        if (savedInstanceState != null) {
            values = savedInstanceState.getParcelable("SI_PARCEL_SENSOR");
            controller = new ModelControllerTemperature(values);
            controller.setHighLimit(savedInstanceState.getDouble("MAX_TEMP"));
            controller.setLowLimit(savedInstanceState.getDouble("MIN_TEMP"));
        }

        //Recherche de l'intent explicite et implicite
        Intent intent = getIntent();
        if (intent.hasExtra("SENSOR_PARCEL")) {
            values = intent.getParcelableExtra("SENSOR_PARCEL");
            controller = new ModelControllerTemperature(values);
        }

        updateTextCount();
        addGraphData();
    }

    /**
     * Ajoute les valeurs de SensorData dans le graphique
     * documentation sur les graphiques consultée: https://github.com/jjoe64/GraphView/wiki/Simple-graph
     */
    private void addGraphData(){
        DataPoint[] temperatureData = new DataPoint[values.getValues().length];

        int i = 0;
        for(SensorValue sensorValues: values.getValues()){
            temperatureData[i] = new DataPoint(sensorValues.getTimeStamp(), sensorValues.getValue());
            i++;
        }

        LineGraphSeries<DataPoint> temperatureSeries = new LineGraphSeries<DataPoint>(temperatureData);
        temperatureSeries.setTitle(getResources().getString(R.string.temperatureWelcome));
        temperatureSeries.setColor(Color.BLACK);
        temperatureSeries.setDrawDataPoints(true);

        updateMaxAndMinGraph();

        graph.addSeries(temperatureSeries);
        graph.getLegendRenderer().setVisible(true);
    }

    /**
     * Ajoute et modifie les valeurs maximales et minimales dans le graphique
     */
    private void updateMaxAndMinGraph(){
        //Les valeurs entrées précédement sont supprimées si elles existaient déjà
        graph.removeSeries(maxSeries);
        graph.removeSeries(minSeries);

        long maxTimeStamp = values.getValues()[values.getValues().length - 1].getTimeStamp();
        DataPoint[] maxTemperatureData = new DataPoint[2];
        DataPoint[] minTemperatureData = new DataPoint[2];

        //Ajout des valeurs
        maxTemperatureData[0] = new DataPoint(0, controller.getHighLimit());
        maxTemperatureData[1] = new DataPoint(maxTimeStamp, controller.getHighLimit());
        minTemperatureData[0] = new DataPoint(0, controller.getLowLimit());
        minTemperatureData[1] = new DataPoint(maxTimeStamp, controller.getLowLimit());

        //Ajout des données dans le graphique
        maxSeries = new LineGraphSeries<>(maxTemperatureData);
        maxSeries.setTitle(getResources().getString(R.string.maxTemperature));
        maxSeries.setColor(Color.BLUE);

        minSeries = new LineGraphSeries<>(minTemperatureData);
        minSeries.setTitle(getResources().getString(R.string.minTemperature));
        minSeries.setColor(Color.RED);

        graph.addSeries(maxSeries);
        graph.addSeries(minSeries);
    }

    /**
     * Modifie les champs de textes avec les valeurs minimales et maximales
     */
    private void updateTextCount(){
        maxTxt.setText(Double.toString(controller.getHighLimit()));
        minTxt.setText(Double.toString(controller.getLowLimit()));
    }

    public void onUpMaxButton(View view) {
        controller.onHighLimitUp();
        updateTextCount();
        updateMaxAndMinGraph();
    }

    public void onUpMinButton(View view) {
        controller.onLowLimitUp();
        updateTextCount();
        updateMaxAndMinGraph();
    }

    public void onDownMaxButton(View view) {
        controller.onHighLimitDown();
        updateTextCount();
        updateMaxAndMinGraph();
    }

    public void onDownMinButton(View view) {
        controller.onLowLimitDown();
        updateTextCount();
        updateMaxAndMinGraph();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("SI_PARCEL_SENSOR", this.values);
        outState.putDouble("MAX_TEMP", controller.getHighLimit());
        outState.putDouble("MIN_TEMP", controller.getLowLimit());
    }

    private void addNewSensorDataStat(){
        long elapsedTime = Calendar.getInstance().getTime().toInstant().toEpochMilli();

        sensorDataStats.setSensorID(SensorID.TEMPERATURE_ID);
        sensorDataStats.setTimeStamp(elapsedTime);
        sensorDataStats.setMax(controller.getHighLimit());
        sensorDataStats.setMin(controller.getLowLimit());

        repoSensorData.insert(sensorDataStats);
    }

    public void onSaveData(View view) {
        addNewSensorDataStat();
        onBackPressed();
    }

}