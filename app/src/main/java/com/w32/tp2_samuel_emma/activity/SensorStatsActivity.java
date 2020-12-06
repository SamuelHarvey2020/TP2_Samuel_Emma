package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView maxTxt;
    private TextView minTxt;
    private Spinner spinner;
    private long selectedTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_stats);

        databaseFactory = new MyDatabaseFactory(this);
        repoStat = new SensorDataRepository(databaseFactory.getWritableDatabase());

        maxTxt = findViewById(R.id.maxValue);
        minTxt = findViewById(R.id.minValue);
        spinner = findViewById(R.id.timeStampSpinner);

        //ajout de données dans le spinner
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
        //TODO: changer paramètre de DisplayDataInfo à la variable selectedTimeStamp
        long timeStamp = dataList.get(0).getTimeStamp();
        displayDataInfo(timeStamp);
    }

    public void onBackButtonPress(View view) {
        onBackPressed();
    }

    public void onClickHumidity(View view) {
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
        //TODO: changer paramètre de DisplayDataInfo à la variable selectedTimeStamp
        displayDataInfo(dataList.get(0).getTimeStamp());
    }

    public void onClickTemperature(View view) {
        populateSpinnerWithData(SensorID.TEMPERATURE_ID);
        //TODO: changer paramètre de DisplayDataInfo à la variable selectedTimeStamp
        displayDataInfo(dataList.get(0).getTimeStamp());
    }

    private void displayDataInfo(long timeStamp){
        SensorDataStats sensor = repoStat.findWithTimeStamp(timeStamp);
        maxTxt.setText(Double.toString(sensor.getMax()));
        minTxt.setText(Double.toString(sensor.getMin()));
    }

    private void populateSpinnerWithData(SensorID id){
        List<SensorDataStats> list = repoStat.findAll(id);
        this.dataList = list;
        populateSpinner();
    }

    private void populateSpinner(){

    }
}