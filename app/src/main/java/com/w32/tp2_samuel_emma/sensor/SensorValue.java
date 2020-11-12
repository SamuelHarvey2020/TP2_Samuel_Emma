package com.w32.tp2_samuel_emma.sensor;

public class SensorValue{
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

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public double getValue()
    {
        return value;
    }
}

