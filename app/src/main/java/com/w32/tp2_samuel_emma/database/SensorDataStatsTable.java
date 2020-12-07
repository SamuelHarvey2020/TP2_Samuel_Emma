package com.w32.tp2_samuel_emma.database;

public class SensorDataStatsTable {
    public static final String CREATE_TABLE_SQL = "" +
            "CREATE TABLE sensorDataStats (" +
            "       id                     INTEGER          PRIMARY KEY       AUTOINCREMENT, " +
            "       sensorID               TEXT, " +
            "       timeStamp              LONG, " +
            "       min                    DOUBLE, " +
            "       max                    DOUBLE " +
            ")";

    public static final String DROP_TABLE_SQL = "" +
            "DROP TABLE sensorDataStats";

    public static final String INSERT_SQL = "" +
            "INSERT INTO sensorDataStats ( " +
            "        sensorID, " +
            "        timeStamp, " +
            "        min, " +
            "        max " +
            ") VALUES ( " +
            "        ?, " +
            "        ?, " +
            "        ?, " +
            "        ? " +
            ")";

    public static final String SELECT_ALL_OF_SQL = "" +
            "SELECT " +
            "        sensorDataStats.sensorID, " +
            "        sensorDataStats.timeStamp, " +
            "        sensorDataStats.min, " +
            "        sensorDataStats.max " +
            "FROM " +
            "        sensorDataStats ";

    public static final String SELECT_ONE_OF_SQL = "" +
            "SELECT " +
            "        sensorDataStats.sensorID, " +
            "        sensorDataStats.timeStamp, " +
            "        sensorDataStats.min, " +
            "        sensorDataStats.max " +
            "FROM " +
            "        sensorDataStats " +
            "WHERE " +
            "        sensorDataStats.sensorID = ?";


    public static final String SELECT_LAST_OF_SQL = "" +
            "SELECT " +
            "        MAX(id), " +
            "        sensorDataStats.sensorID, " +
            "        sensorDataStats.timeStamp, " +
            "        sensorDataStats.min, " +
            "        sensorDataStats.max " +
            "FROM " +
            "sensorDataStats ";


    public static final String UPDATE_SQL = "" +
            "UPDATE sensorDataStats " +
            "SET " +
            "        sensorID = ?, " +
            "        timeStamp = ?, " +
            "        min = ?, " +
            "        max = ? " +
            "WHERE " +
            "        id = ?";

    public static final String DELETE_SQL = "" +
            "DELETE FROM sensorDataStats " +
            "WHERE " +
            "        id = ?";

    public static final String SELECT_ONE_WITH_TIMESTAMP_SQL = "" +
            "SELECT " +
            "        sensorDataStats.id, " +
            "        sensorDataStats.sensorID, " +
            "        sensorDataStats.timeStamp, " +
            "        sensorDataStats.min, " +
            "        sensorDataStats.max " +
            "FROM " +
            "        sensorDataStats " +
            "WHERE " +
            "        sensorDataStats.timeStamp = ?";

    private SensorDataStatsTable(){

    }
}
