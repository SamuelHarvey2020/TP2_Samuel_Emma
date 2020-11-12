package com.w32.tp2_samuel_emma.sensor;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorData implements Parcelable {
    SensorID id = SensorID.TEMPERATURE_ID;

    SensorValue[] values;

    public SensorData(SensorID id,
                      SensorValue[] values)
    {
        this.values= values;
        this.id= id;
    }

    protected SensorData(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SensorData> CREATOR = new Creator<SensorData>() {
        @Override
        public SensorData createFromParcel(Parcel in) {
            return new SensorData(in);
        }

        @Override
        public SensorData[] newArray(int size) {
            return new SensorData[size];
        }
    };

    public SensorValue[] getValues()
    {
        return values;
    }
    public SensorID getId()
    {
        return id;
    }
}

