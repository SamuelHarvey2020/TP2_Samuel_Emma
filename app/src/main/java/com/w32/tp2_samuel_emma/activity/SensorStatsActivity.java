package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.w32.tp2_samuel_emma.R;
import com.w32.tp2_samuel_emma.data.SensorDataStats;
import com.w32.tp2_samuel_emma.database.MyDatabaseFactory;
import com.w32.tp2_samuel_emma.repository.SensorDataRepository;
import com.w32.tp2_samuel_emma.sensor.SensorID;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SensorStatsActivity extends AppCompatActivity {

    private MyDatabaseFactory databaseFactory;
    private SensorDataRepository repoStat;
    private List<SensorDataStats> dataList;
    private TextView maxTxt;
    private TextView minTxt;
    private Spinner spinner;
    ArrayAdapter<String> adapter;
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
        long timeStamp = this.selectedTimeStamp;
        displayDataInfo(timeStamp);
    }

    public void onBackButtonPress(View view) {
        onBackPressed();
    }

    public void onClickHumidity(View view) {
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
        //TODO: changer paramètre de DisplayDataInfo à la variable selectedTimeStamp
        displayDataInfo(this.selectedTimeStamp);
    }

    public void onClickTemperature(View view) {
        populateSpinnerWithData(SensorID.TEMPERATURE_ID);
        //TODO: changer paramètre de DisplayDataInfo à la variable selectedTimeStamp
        displayDataInfo(this.selectedTimeStamp);
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

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.clear();
        for(int i = 0; i<this.dataList.size();i++){

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Timestamp ts = new Timestamp(this.dataList.get(i).getTimeStamp());
            String formattedDate = sdf.format(ts);
            spinnerArray.add(formattedDate);
        }

        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) spinner;
        sItems.setAdapter(adapter);

        int tsPosition = sItems.getSelectedItemPosition();
        this.selectedTimeStamp = this.dataList.get(tsPosition).getTimeStamp();
    }
}