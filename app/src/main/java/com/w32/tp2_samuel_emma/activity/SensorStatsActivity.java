package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.database.MyDatabaseFactory;
import com.w32.tp2_samuel_emma.repository.SensorDataRepository;

public class SensorStatsActivity extends AppCompatActivity {

    private MyDatabaseFactory databaseFactory;
    private SensorDataRepository repoStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_stats);

        databaseFactory = new MyDatabaseFactory(this);
        repoStat = new SensorDataRepository(databaseFactory.getWritableDatabase());
    }

    public void onBackButtonPress(View view) {
        onBackPressed();
    }

    public void onClickHumidity(View view) {
    }

    public void onClickTemperature(View view) {
    }
}