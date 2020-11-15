package com.w32.tp2_samuel_emma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private SensorData sensor;
    private TextView tvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessages = findViewById(R.id.tv_messagesText);

        if (savedInstanceState != null) {
            sensor = savedInstanceState.getParcelable("SI_PARCEL_SENSOR");
            tvMessages.setText(savedInstanceState.getString("SI_TEXT"));
        }

        //Recherche d'un intent implicite si il y a lieu
        Intent impIntent = getIntent();
        if("text/plain".equals(impIntent.getType())) {
            String sensorID = impIntent.getStringExtra(Intent.EXTRA_TEXT);
            SensorID id = SensorID.valueOf(sensorID);
            try {
                this.sensor = this.getSensorFromJSON(id);
                showDataCount(sensor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent expIntent = new Intent(this, WeatherActivity.class);
            expIntent.putExtra("SENSOR_PARCEL", this.sensor);
            startActivity(expIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("SI_PARCEL_SENSOR", this.sensor);
        outState.putString("SI_TEXT", this.tvMessages.getText().toString());
    }

    /***
     * Quand le bouton "Start Humidity" est appuyé
     * Affiche le nombre de données dans l'activité principale et redirige dans la deuxième activité
     * @param view
     * @throws IOException : exception JSON
     */
    public void onStartHumidity(View view) throws IOException {
        Intent intent = new Intent(this, WeatherActivity.class);
        SensorID id = SensorID.HUMIDITY_ID;
        this.sensor = getSensorFromJSON(id);

        intent.putExtra("SENSOR_PARCEL", this.sensor);
        showDataCount(sensor);

        startActivity(intent);
    }

    /***
     * Quand le bouton "Start Temperature" est appuyé
     * Affiche le nombre de données dans l'activité principale et redirige dans la deuxième activité
     * @param view
     * @throws IOException : exception JSON
     */
    public void onStartTemperature(View view) throws IOException {
        Intent intent = new Intent(this, WeatherActivity.class);
        SensorID id = SensorID.TEMPERATURE_ID;
        this.sensor = getSensorFromJSON(id);

        intent.putExtra("SENSOR_PARCEL", this.sensor);
        showDataCount(sensor);

        startActivity(intent);
    }

    /***
     * Affichage dans l'activité principale
     * Affiche le nombre de données et si les données sont valides
     * @param sensor : sensor contenant les données
     */
    private void showDataCount(SensorData sensor){
        String output;
        if(sensor.getValues() != null){
            int count = sensor.getValues().length;
            output = getApplicationContext().getString(R.string.success) + "\n" + count + " "
                    + getApplicationContext().getString(R.string.data_read);
        }
        else{
            output = getApplicationContext().getString(R.string.error);
        }

        this.tvMessages.setText(output);
    }

    /***
     * Va chercher les données des sensor à partir d'un document JSON
     * @param id : ID du sensor, HUMIDITY_ID ou TEMPERATURE_ID
     * @return : le sensor contenant les données
     * @throws IOException : Exception JSON
     */
    private SensorData getSensorFromJSON(SensorID id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(readJson(id), SensorData.class);
    }

    /***
     * Lis les fichiers JSON
     * SOURCE CODE: https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources
     * @param id : ID du sensor, HUMIDITY_ID ou TEMPERATURE_ID
     * @return : un String contenant les données
     * @throws IOException : exception JSON
     */
    private String readJson(SensorID id) throws IOException {

        InputStream inputStream = null;

        if(id == SensorID.HUMIDITY_ID){
            inputStream = getResources().openRawResource(R.raw.humidity);
        }
        else if(id == SensorID.TEMPERATURE_ID){
            inputStream = getResources().openRawResource(R.raw.temperature);
        }

        String jsonString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        while (line != null) {
            line = reader.readLine();
            jsonString += line;
        }

        return jsonString;
    }
}