package com.w32.tp2_samuel_emma.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorID;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorData sensor;
    private SensorValue[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent impIntent = getIntent();
        if("text/plain".equals(impIntent.getType())) {
            String sensorID = impIntent.getStringExtra(Intent.EXTRA_TEXT);
            SensorID id = SensorID.valueOf(sensorID);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                SensorData data = objectMapper.readValue(readJson(id), SensorData.class);
                this.sensor = data;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent expIntent = new Intent(this, WeatherActivity.class);
            expIntent.putExtra("SENSOR_PARCEL", this.sensor);
            startActivity(expIntent);
        }
    }

    public void onStartHumidity(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);
        this.sensor = new SensorData(SensorID.HUMIDITY_ID, values);
        intent.putExtra("SENSOR_PARCEL", this.sensor);
        startActivity(intent);
    }

    public void onStartTemperature(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);
        this.sensor = new SensorData(SensorID.TEMPERATURE_ID, values);
        intent.putExtra("SENSOR_PARCEL", this.sensor);
        startActivity(intent);
    }

    //https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources
    public String readJson(SensorID id) throws IOException {

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