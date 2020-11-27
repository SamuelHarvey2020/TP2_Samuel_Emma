package com.w32.tp2_samuel_emma.model;

import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerHumidity {
	public static final double EPSILON = 0.001;
    private static BarGraphSeries<DataPoint> series;
    private int nbZones = 5;
	private SensorValue[] sensorDataValues;
	private DataPoint[] dataPointTable ;
	//Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public ModelControllerHumidity(SensorData p)
    {
		// A COMPLETER
        this.sensorDataValues = p.getValues();
        double span = this.calculateSpan();
        double zoneSpan = span / nbZones;

        this.series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1,12)
        });
    }

    private double calculateSpan() {

        double[] doubleValues = new double[12];
        int counter = 0;
        for (SensorValue value:this.sensorDataValues) {
            doubleValues[counter] = value.getValue();
            counter++;
        }

        double highestValue = -273.15;  // valeur de la plus basse température possible
        double lowestValue = 1000;      // haute température pour etre sur
        for(int i = 0;i < doubleValues.length; i++){

            if (doubleValues[i] > highestValue){
                highestValue = doubleValues[i];
            }
            if (doubleValues[i] < lowestValue){
                lowestValue = doubleValues[i];
            }
        }

        double span = highestValue - lowestValue;
        return span;
    }

    public static BarGraphSeries<DataPoint> getSeries() {

        return series;
    }

    public void setNbZones(int nbZones)
    {
        // A COMPLETER
        this.nbZones = nbZones;
    }

    public int getNbZones()
    {
		// A COMPLETER
		return this.nbZones;
    }


    public int getCountInZone(int zone)
    {
		// A COMPLETER        
		return 0;
    }

    public double getUpperLimit(int zone)
    {
        // A COMPLETER
		return 0;
    }

    public double getLowerLimit(int zone)
    {
        // A COMPLETER
		return 0;
    }


    public int getSelectedZone()
    {
        // A COMPLETER
		return 0;
    }

    public void setSelectedZone(int selectedZone)
    {
        // A COMPLETER
    }
}
