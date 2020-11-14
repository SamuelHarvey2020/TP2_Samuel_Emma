package com.w32.tp2_samuel_emma.sensor;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorData implements Parcelable {
    SensorID id = SensorID.TEMPERATURE_ID;

    SensorValue[] values;

    @JsonCreator
    public SensorData(@JsonProperty("id") SensorID id,
                      @JsonProperty("datas") SensorValue[] values)
    {
        this.values= values;
        this.id= id;
    }

    //Lecture / écriture de tableaux : https://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
    //Lecture / écriture d'enum: https://stackoverflow.com/questions/38174961/how-to-read-and-write-enum-into-parcel-on-android
    protected SensorData(Parcel in) {
        values = in.createTypedArray(SensorValue.CREATOR);
        id = (SensorID) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(values, 0);
        dest.writeSerializable(id);
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

    @JsonProperty("datas")
    public SensorValue[] getValues()
    {
        return values;
    }

    @JsonProperty("id")
    public SensorID getId()
    {
        return id;
    }
}

