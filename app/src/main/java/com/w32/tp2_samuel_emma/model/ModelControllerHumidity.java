package com.w32.tp2_samuel_emma.model;

import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w32.tp2_samuel_emma.sensor.SensorData;
import com.w32.tp2_samuel_emma.sensor.SensorValue;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerHumidity {
	public static final double EPSILON = 0.001;
    public static BarGraphSeries<DataPoint> series;
    private double lowestValue;
    private int nbZones = 5;
    private double zoneSpan;
	private SensorValue[] sensorDataValues;
    private int selectedZone;

    //Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public ModelControllerHumidity(SensorData p)
    {
        this.sensorDataValues = p.getValues();
        this.selectedZone = 1;
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
            if(zone == nbZones){
                if (doubleValues[i] >= getLowerLimit(zone) &&
                    doubleValues[i] <= getUpperLimit(zone)) {
                    zoneCount++;
                }
            }
            else if (doubleValues[i] >= getLowerLimit(zone) &&
                doubleValues[i] <= getUpperLimit(zone)-EPSILON){
                zoneCount++;
            }
        }

		return zoneCount;
    }

    public double getUpperLimit(int zone)
    {
        double upLimit = (this.getLowerLimit(zone) + zoneSpan);
		return upLimit;
    }

    public double getLowerLimit(int zone)
    {
        double lowLimit = (this.lowestValue + (zoneSpan*(zone-1)));
		return lowLimit;
    }


    public int getSelectedZone()
    {
		return this.selectedZone;
    }

    public void setSelectedZone(int selectedZone)
    {
        this.selectedZone = selectedZone;
    }

    public void setLowestValue(double lowestValue) {
        this.lowestValue = lowestValue;
    }
    public void setZoneSpan(double zoneSpan){
        this.zoneSpan = zoneSpan;
    }
}
