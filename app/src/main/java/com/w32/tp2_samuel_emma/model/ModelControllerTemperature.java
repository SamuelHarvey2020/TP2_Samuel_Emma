package com.w32.tp2_samuel_emma.model;

import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerTemperature{
    SensorData values;
    SensorValue[] valuesArray;
    double maxValue;
    double minValue;

	public ModelControllerTemperature(SensorData p)
    {
       this.values = p;
       this.valuesArray = values.getValues();
       resetMaxAndMinTemperature();
    }
	//Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public void setHighLimit(double highLimit)
    {
        this.maxValue = highLimit;
    }
	
	public double getHighLimit()
    {
        return Math.round(maxValue * 100.0) / 100.0;
    }

    public void setLowLimit(double lowLimit)
    {
        this.minValue = lowLimit;
    }	

    public double getLowLimit()
    {
        return Math.round(minValue * 100.0) / 100.0;
    }

    public void onHighLimitUp() 
	{
        maxValue = maxValue + (maxValue - minValue)/10;
    }

    public void onHighLimitDown() 
	{
	    if(maxValue - (maxValue - minValue)/10 > minValue){
            maxValue = maxValue - (maxValue - minValue)/10;
        }
    }

    public void onLowLimitDown() 
	{
	    if(minValue - (maxValue - minValue)/10 >= 0){
            minValue = minValue - (maxValue - minValue)/10;
        }
    }

    public void onLowLimitUp() 
	{
	    if(minValue + (maxValue - minValue)/10 < maxValue){
            minValue = minValue + (maxValue - minValue)/10;
        }
    }

    private List<Double> valuesToArrayList(){
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < valuesArray.length; i++) {
            list.add(valuesArray[i].getValue());
        }
        return list;
    }

    private void resetMaxAndMinTemperature(){
	    setHighLimit(Collections.max(valuesToArrayList()));
	    setLowLimit(Collections.min(valuesToArrayList()));
    }
}
