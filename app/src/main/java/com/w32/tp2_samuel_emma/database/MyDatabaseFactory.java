package com.w32.tp2_samuel_emma.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.w32.tp2_samuel_emma.sensor.SensorID;

public class MyDatabaseFactory extends SQLiteOpenHelper {

    //S'il faut faire une grosse modif à la BD, changer le nom de la BD
    //Sinon, la BD ne sera jamais recréée
    private static final String DATABASE_NAME = "TP2bPartie2";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseFactory(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    public MyDatabaseFactory(Context context) {
        this(context, DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SensorDataStatsTable.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SensorDataStatsTable.DROP_TABLE_SQL);
        onCreate(db);
    }
}