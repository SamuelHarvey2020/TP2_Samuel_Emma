package com.w32.tp2_samuel_emma.sensor;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorValue implements Parcelable {
    // Valeur sur l'axe de X
    long timeStamp;

    // Valeur sur l'axe de Y
    double value;


    public SensorValue(long timestamp,
                       double value)
    {
        this.value= value;
        this.timeStamp = timestamp;
    }

    protected SensorValue(Parcel in) {
        timeStamp = in.readLong();
        value = in.readDouble();
    }

    public static final Creator<SensorValue> CREATOR = new Creator<SensorValue>() {
        @Override
        public SensorValue createFromParcel(Parcel in) {
            return new SensorValue(in);
        }

        @Override
        public SensorValue[] newArray(int size) {
            return new SensorValue[size];
        }
    };

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public double getValue()
    {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(timeStamp);
        parcel.writeDouble(value);
    }
}

