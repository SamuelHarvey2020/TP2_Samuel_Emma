package com.w32.tp2_samuel_emma.data;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorDataStats implements Parcelable{

    private int id;
    private String sensorID;
    private long timeStamp;
    private double min;
    private double max;

    public SensorDataStats()
    {
        this(0, "", 0, 0, 0);
    }

    public SensorDataStats(int id, String sensorID, long timeStamp, double min, double max)
    {
        this.id = id;
        this.sensorID = sensorID;
        this.timeStamp = timeStamp;
        this.min = min;
        this.max = max;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
    protected SensorDataStats(Parcel in) {
    }

    public static final Creator<SensorDataStats> CREATOR = new Creator<SensorDataStats>() {
        @Override
        public SensorDataStats createFromParcel(Parcel in) {
            return new SensorDataStats(in);
        }

        @Override
        public SensorDataStats[] newArray(int size) {
            return new SensorDataStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

}
