package com.w32.tp2_samuel_emma.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.w32.tp2_samuel_emma.data.SensorDataStats;
import com.w32.tp2_samuel_emma.database.SensorDataStatsTable;

public class SensorDataRepository implements Repository<SensorDataStats> {

    private SQLiteDatabase database;

    public SensorDataRepository(SQLiteDatabase database) {
        this.database = database;
    }

    //Implémentation des méthodes de l'interface avec le "T" remplacé par l'objet de données
    @Override
    public boolean insert(SensorDataStats sensorDataStats) {
        try{
            database.execSQL(SensorDataStatsTable.INSERT_SQL, new String[]
                    {
                            sensorDataStats.getSensorID(),
                            String.valueOf(sensorDataStats.getTimeStamp()),
                            String.valueOf(sensorDataStats.getMin()),
                            String.valueOf(sensorDataStats.getMax()),
                    });
            return true;
        }catch(RuntimeException e){
            return false;
        }
    }

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
                            sensorDataStats.getSensorID(),
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
                    sensorDataStats.setSensorID(cursor.getString(1));
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
}
