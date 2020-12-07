package com.w32.tp2_samuel_emma.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.material.snackbar.Snackbar;
import com.w32.tp2_samuel_emma.data.SensorDataStats;
import com.w32.tp2_samuel_emma.database.SensorDataStatsTable;
import com.w32.tp2_samuel_emma.sensor.SensorID;

import java.util.ArrayList;
import java.util.List;

public class SensorDataRepository implements Repository<SensorDataStats> {

    private SQLiteDatabase database;

    public SensorDataRepository(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Insertion de données dans la BD
     * @param sensorDataStats : données à insérer
     * @return true si l'opération s'est effectuée correctement, false sinon
     */
    @Override
    public boolean insert(SensorDataStats sensorDataStats) {
        try{
            database.execSQL(SensorDataStatsTable.INSERT_SQL, new String[]
                    {
                            String.valueOf(sensorDataStats.getSensorID()),
                            String.valueOf(sensorDataStats.getTimeStamp()),
                            String.valueOf(sensorDataStats.getMin()),
                            String.valueOf(sensorDataStats.getMax()),
                    });
            return true;
        }catch(RuntimeException e){
            return false;
        }
    }

    /**
     * mise à jour de la BD
     * @param sensorDataStats : données à modifier
     * @return true si l'opération s'est effectuée correctement, false sinon
     */
    @Override
    public boolean update(SensorDataStats sensorDataStats) {
        //Toujours utiliser un try-catch, ce genre de manipulations peut générer des exceptions de toutes sortes
        try
        {
            //Important : on débute une transaction
            database.beginTransaction();

            database.execSQL(SensorDataStatsTable.UPDATE_SQL, new String[]
                    {
                            String.valueOf(sensorDataStats.getId()),
                            String.valueOf(sensorDataStats.getSensorID()),
                            String.valueOf(sensorDataStats.getTimeStamp()),
                            String.valueOf(sensorDataStats.getMin()),
                            String.valueOf(sensorDataStats.getMax()),
                    });

            //À ce stade, la transaction est un succès
            database.setTransactionSuccessful();
            return true;
        }
        catch(RuntimeException e)
        {
            return false;
        }
    }

    @Override
    public SensorDataStats find(int id) {
        return null;
    }

    /**
     * trouve le dernier item de la BD
     * @return null si la transaction ne s'est pas effectuée correctement, le sensorDataStat trouvé dans la BD sinon
     */
    public SensorDataStats findLast() {
        SensorDataStats sensorDataStats = null;
        Cursor cursor = null;
        try
        {
            database.beginTransaction();
            cursor = database.rawQuery(SensorDataStatsTable.SELECT_LAST_OF_SQL, new String[]{});

            if (cursor.moveToNext())
            {
                if(cursor.getLong(0) > 0)
                {
                    sensorDataStats = new SensorDataStats();
                    sensorDataStats.setId(cursor.getInt(0));
                    if(cursor.getString(1) == String.valueOf(SensorID.TEMPERATURE_ID))
                        sensorDataStats.setSensorID(SensorID.TEMPERATURE_ID);
                    else
                        sensorDataStats.setSensorID(SensorID.HUMIDITY_ID);
                    sensorDataStats.setTimeStamp(Long.parseLong(cursor.getString(2)));
                    sensorDataStats.setMin(Double.parseDouble(cursor.getString(3)));
                    sensorDataStats.setMax(Double.parseDouble(cursor.getString(4)));
                }
            }
            database.setTransactionSuccessful();
        }
        catch(RuntimeException e)
        {
            return null;
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
            database.endTransaction();
        }
        return sensorDataStats;
    }

    /**
     * supprime un élément de la liste
     * @param sensorDataStats : élément à supprimer
     * @return true si l'opération s'est effectuée correctement, false sinon
     */
    @Override
    public boolean delete(SensorDataStats sensorDataStats) {
        try{
            database.execSQL(SensorDataStatsTable.DELETE_SQL, new String[]
                    {String.valueOf(sensorDataStats.getId())});
            return true;
        }catch(RuntimeException e){
            return false;
        }
    }

    /**
     * supprime un élément de la liste
     * @param id : id de l'élément à supprimer
     * @return true si l'opération s'est effectuée correctement, false sinon
     */
    @Override
    public boolean delete(int id) {
        try{
            database.execSQL(SensorDataStatsTable.DELETE_SQL, new String[]
                    {String.valueOf(id)});
            return true;
        }catch(RuntimeException e){
            return false;
        }
    }

    /**
     * trouve tous les éléments selon leur SensorID
     * @param id : TEMPERATURE_ID ou HUMIDITY_ID
     * @return une liste avec tous les éléments de température ou d'humidité trouvés
     */
    public List<SensorDataStats> findAll(SensorID id)
    {
        List<SensorDataStats> sensorList = new ArrayList<>();
        Cursor cursor = null;

        try{
            //on débute une transaction
            database.beginTransaction();
            cursor = database.rawQuery(SensorDataStatsTable.SELECT_ONE_OF_SQL, new String[]{String.valueOf(id)});

            while(cursor.moveToNext()){
                SensorDataStats sensor = new SensorDataStats();
                if(cursor.getString(0) == String.valueOf(SensorID.TEMPERATURE_ID)){
                    sensor.setSensorID(SensorID.TEMPERATURE_ID);
                }
                else{
                    sensor.setSensorID(SensorID.HUMIDITY_ID);
                }
                sensor.setTimeStamp(cursor.getLong(1));
                sensor.setMin(cursor.getDouble(2));
                sensor.setMax(cursor.getDouble(3));

                sensorList.add(sensor);
            }

            database.setTransactionSuccessful();
        }
        catch(RuntimeException e){
            return null;
        }

        finally
        {
            if (cursor != null) {
                cursor.close();
            }
            database.endTransaction();
        }

        return sensorList;
    }

    /**
     * Trouve un sensorDataStat selon son timeStamp
     * @param timeStamp : timeStamp permettant d'identifier le bon sensorDataStats
     * @return le bon SensorDataStats
     */
    public SensorDataStats findWithTimeStamp(long timeStamp){
        SensorDataStats sensor = new SensorDataStats();
        Cursor cursor = null;

        try{
            //on débute une transaction
            database.beginTransaction();
            cursor = database.rawQuery(SensorDataStatsTable.SELECT_ONE_WITH_TIMESTAMP_SQL, new String[]{String.valueOf(timeStamp)});

            while(cursor.moveToNext()){
                if(cursor.getString(1) == String.valueOf(SensorID.TEMPERATURE_ID)){
                    sensor.setSensorID(SensorID.TEMPERATURE_ID);
                }
                else{
                    sensor.setSensorID(SensorID.HUMIDITY_ID);
                }
                sensor.setTimeStamp(cursor.getLong(2));
                sensor.setMin(cursor.getDouble(3));
                sensor.setMax(cursor.getDouble(4));
            }

            database.setTransactionSuccessful();
        }
        catch(RuntimeException e){
            return null;
        }

        finally
        {
            if (cursor != null) {
                cursor.close();
            }
            database.endTransaction();
        }

        return sensor;
    }
}
