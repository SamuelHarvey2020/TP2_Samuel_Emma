package com.w32.tp2_samuel_emma.model;

import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Modifie la valeur maximale
     * @param highLimit valeur maximale
     */
    public void setHighLimit(double highLimit)
    {
        this.maxValue = highLimit;
    }

    /**
     * @return la valeur maximale arrondie
     */
	public double getHighLimit()
    {
        return Math.round(maxValue * 100.0) / 100.0;
    }

    /**
     * Modifie la valeur minimale
     * @param lowLimit valeur minimale
     */
    public void setLowLimit(double lowLimit)
    {
        this.minValue = lowLimit;
    }

    /**
     * @return la valeur minimale arrondie
     */
    public double getLowLimit()
    {
        return Math.round(minValue * 100.0) / 100.0;
    }

    /**
     * Augmente la valeur maximale d'un pas
     */
    public void onHighLimitUp() 
	{
        maxValue = maxValue + (maxValue - minValue)/10;
    }

    /**
     * Diminue la valeur maximale d'un pas
     */
    public void onHighLimitDown() 
	{
	    if(maxValue - (maxValue - minValue)/10 > minValue){
            maxValue = maxValue - (maxValue - minValue)/10;
        }
    }

    /**
     * Diminue la valeur minimale d'un pas
     */
    public void onLowLimitDown() 
	{
	    if(minValue - (maxValue - minValue)/10 >= 0){
            minValue = minValue - (maxValue - minValue)/10;
        }
    }

    /**
     * Augmente la valeur minimale d'un pas
     */
    public void onLowLimitUp() 
	{
	    if(minValue + (maxValue - minValue)/10 < maxValue){
            minValue = minValue + (maxValue - minValue)/10;
        }
    }

    /**
     * Va chercher toutes les values de SensorValue pour les mettre dans une liste
     * @return une liste avec toutes les valeurs
     */
    private List<Double> valuesToArrayList(){
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < valuesArray.length; i++) {
            list.add(valuesArray[i].getValue());
        }
        return list;
    }

    /***
     * Ajuste les valeurs max et min selon les valeurs de SensorValue
     */
    private void resetMaxAndMinTemperature(){
	    setHighLimit(Collections.max(valuesToArrayList()));
	    setLowLimit(Collections.min(valuesToArrayList()));
    }
}
