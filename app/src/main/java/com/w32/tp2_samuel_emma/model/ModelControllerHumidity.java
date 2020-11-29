package com.w32.tp2_samuel_emma.model;

import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerHumidity {
	public static final double EPSILON = 0.001;
    private static BarGraphSeries<DataPoint> series;
    private double highestValue;
    private double lowestValue;
    private int nbZones = 1;
    double zoneSpan;
	private SensorValue[] sensorDataValues;

	//Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public ModelControllerHumidity(SensorData p)
    {
		// A COMPLETER
        this.sensorDataValues = p.getValues();
        double span = this.calculateSpan();
        this.zoneSpan = span / nbZones;

        this.series = new BarGraphSeries<DataPoint>();

        int counter = 0;
        while(counter != nbZones){
            counter ++;
            int countInZone = getCountInZone(counter);
            series.appendData(new DataPoint(counter,countInZone), true, nbZones);
        }

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
        this.highestValue = highestValue;
        this.lowestValue = lowestValue;


        double span = highestValue - lowestValue;
        return span;
    }

    public static BarGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public void setNbZones(int nbZones)
    {
        this.nbZones = nbZones;
    }

    public int getNbZones()
    {
		return this.nbZones;
    }


    public int getCountInZone(int zone)
    {
		// A COMPLETER
        int zoneCount = 0;

        double[] doubleValues = new double[12];
        int counter = 0;
        for (SensorValue value:this.sensorDataValues) {
            doubleValues[counter] = value.getValue();
            counter++;
        }

        for (int i = 0; i< doubleValues.length; i++){
            if (doubleValues[i] >= getLowerLimit(zone) && doubleValues[i] <= getUpperLimit(zone)-EPSILON){
                zoneCount++;
            }
        }

		return zoneCount;
    }

    public double getUpperLimit(int zone)
    {
		return (this.lowestValue * (zone+1));
    }

    public double getLowerLimit(int zone)
    {
		return (this.lowestValue * zone);
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
