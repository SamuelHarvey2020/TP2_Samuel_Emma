package com.w32.tp2_samuel_emma.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorID;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

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
            this.sensor = new SensorData(id, values);
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
}