package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.data.SensorDataStats;
import com.w32.tp2_samuel_emma.database.MyDatabaseFactory;
import com.w32.tp2_samuel_emma.repository.SensorDataRepository;
import com.w32.tp2_samuel_emma.sensor.SensorID;

import java.util.ArrayList;
import java.util.List;

public class SensorStatsActivity extends AppCompatActivity {

    private MyDatabaseFactory databaseFactory;
    private SensorDataRepository repoStat;
    private List<SensorDataStats> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_stats);

        databaseFactory = new MyDatabaseFactory(this);
        repoStat = new SensorDataRepository(databaseFactory.getWritableDatabase());

        //ajout de données dans le spinner
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
    }

    public void onBackButtonPress(View view) {
        onBackPressed();
    }

    public void onClickHumidity(View view) {
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
    }

    public void onClickTemperature(View view) {
        populateSpinnerWithData(SensorID.TEMPERATURE_ID);
    }

    private void populateSpinnerWithData(SensorID id){
        List<SensorDataStats> list = new ArrayList<>();
        //TODO: recherche de données avec findAll à faire
        this.dataList = list;
        populateSpinner();
    }

    private void populateSpinner(){

    }
}