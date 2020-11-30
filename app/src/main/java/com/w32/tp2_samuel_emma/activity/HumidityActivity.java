package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.controller.ModelControllerHumidity;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

public class HumidityActivity extends AppCompatActivity {

    SensorData values;
    SensorValue[] valuesArray;
    private TextView textView;
    ModelControllerHumidity controller;
    EditText txtNbZones = null;
    TextView tvMax = null;
    TextView tvMin = null;
    private Spinner zones;
    private GraphView graph;
    private BarGraphSeries<DataPoint> series;
    private double zoneSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        textView = findViewById(R.id.weatherInfoTxt);
        txtNbZones = findViewById(R.id.txtEnterNbZones);
        tvMax = findViewById(R.id.txtMaxValue);
        tvMin = findViewById(R.id.txtMinValue);
        zones = findViewById(R.id.Zones);
        this.graph = (GraphView) findViewById(R.id.graphHumidity);

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

        createGraph();

        String zonesStr = zones.getSelectedItem().toString();
        int zone = Integer.parseInt(zonesStr);
        controller.setSelectedZone(zone);

        displayMinAndMaxValues();
    }

    /**
     * Détermine les valeurs pertinentes afin de créér le graphique par la suite
     */
    private void createGraph() {
        graph.removeSeries(this.series);

        this.valuesArray = values.getValues();
        double span = calculateSpan();
        this.zoneSpan = span / controller.getNbZones();
        controller.setZoneSpan(this.zoneSpan);

        this.series = new BarGraphSeries<DataPoint>();

        int counter = 0;
        while(counter != controller.getNbZones()){
            counter ++;
            int countInZone = controller.getCountInZone(counter);
            series.appendData(new DataPoint(counter,countInZone), true, 10);
        }

        this.graph.addSeries(this.series);
        series.setSpacing(50);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);

        // optimisation des limites du teableau afin d'avoir un affichage plus propre
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(controller.getNbZones() + 1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(14-controller.getNbZones());
    }


    /**
     * Calcule l'étendue des données avec les données du sensor
     *
     */
    public double calculateSpan() {

        double[] doubleValues = new double[12];
        int counter = 0;
        for (SensorValue value:this.valuesArray) {
            doubleValues[counter] = value.getValue();
            counter++;
        }

        double highestValue = -273.15;  // valeur de la plus basse température possible
        double lowestValue = 1000;      // haute température pour etre sur
        for(int i = 0;i < doubleValues.length; i++){

            if (doubleValues[i] > highestValue){
                highestValue = doubleValues[i];
            }
            if (doubleValues[i] < lowestValue){
                lowestValue = doubleValues[i];
            }
        }

        controller.setLowestValue(lowestValue);

        double span = highestValue - lowestValue;
        return span;
    }

    /**
     * Affiche les données des valeurs minimales et maximales pour une zone spécifique du graphique
     */
    private void displayMinAndMaxValues() {
        int zone = controller.getSelectedZone();
        tvMin.setText(String.valueOf(controller.getLowerLimit(zone)));
        tvMax.setText(String.valueOf(controller.getUpperLimit(zone)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int zone = controller.getSelectedZone();

        outState.putParcelable("SI_PARCEL_SENSOR", this.values);
        outState.putString("SI_TEXT", this.textView.getText().toString());
        outState.putDouble("MAX_TEMP", controller.getUpperLimit(zone));
        outState.putDouble("MIN_TEMP", controller.getLowerLimit(zone));
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

    /**
     * Apelle les fonctions qui modifient les données afin d'effectuer le click du boutton
     */
    public void onApplyClicked(View view){
        onSelectNbZones();
        onDisplayZone();
    }

    public void onSelectNbZones() {
        String nbZonesStr = txtNbZones.getText().toString();
        int nbZones = Integer.parseInt(nbZonesStr);
        controller.setNbZones(nbZones);
        updateGraph();
    }

    private void onDisplayZone() {
        String zonesStr = zones.getSelectedItem().toString();
        int zone = Integer.parseInt(zonesStr);
        controller.setSelectedZone(zone);
        displayMinAndMaxValues();
    }
    /**
     * Retire la série courante et crée un nouveau graphique
     */
    private void updateGraph() {
        this.graph.removeSeries(this.series);
        this.series = null;
        createGraph();
    }
}