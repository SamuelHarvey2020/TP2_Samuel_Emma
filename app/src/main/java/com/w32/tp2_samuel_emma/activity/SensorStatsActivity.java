package com.w32.tp2_samuel_emma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
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
        displayDataInfo(selectedTimeStamp);

        /**
         * Listener pour le spinner, chaque fois qu'on clique, on change le selectedTimeStamp et on affiche les données reliés a celui çi
         * Base du listener trouvé ici : https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
         */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int tsPosition = spinner.getSelectedItemPosition();
                selectedTimeStamp = dataList.get(tsPosition).getTimeStamp();
                displayDataInfo(selectedTimeStamp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    public void onBackButtonPress(View view) {
        onBackPressed();
    }

    public void onClickHumidity(View view) {
        populateSpinnerWithData(SensorID.HUMIDITY_ID);
        displayDataInfo(this.selectedTimeStamp);
    }

    public void onClickTemperature(View view) {
        populateSpinnerWithData(SensorID.TEMPERATURE_ID);
        displayDataInfo(this.selectedTimeStamp);
    }

    /**
     * fais afficher les valeurs maximales et minimales selon l'objet qui possede le timestamp entré en paramètre
     * @param timeStamp le timestamp sélectionné dans le spinner
     */
    private void displayDataInfo(long timeStamp){
        SensorDataStats sensor = repoStat.findWithTimeStamp(timeStamp);
        maxTxt.setText(Double.toString(sensor.getMax()));
        minTxt.setText(Double.toString(sensor.getMin()));
    }

    /**
     * crée la liste des objets de données avec le sensorId entré en paramètre, puis apelle la méthode de population du spinner
     * @param id le id du sensor choisi avec les boutons radio
     */
    private void populateSpinnerWithData(SensorID id){
        List<SensorDataStats> list = repoStat.findAll(id);
        this.dataList = list;
        populateSpinner();
    }

    /**
     * crée dynamiquement une liste de tout les timestamps formattés des objets de la liste de données et popule le spinner avec cette liste.
     */
    private void populateSpinner(){

        List<String> spinnerArray =  new ArrayList<String>();

        for(int i = 0; i<this.dataList.size();i++){

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Timestamp ts = new Timestamp(this.dataList.get(i).getTimeStamp());
            String formattedDate = sdf.format(ts);
            spinnerArray.add(formattedDate);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) spinner;
        sItems.setAdapter(adapter);

        int tsPosition = sItems.getSelectedItemPosition();
        selectedTimeStamp = dataList.get(tsPosition).getTimeStamp();

    }


}