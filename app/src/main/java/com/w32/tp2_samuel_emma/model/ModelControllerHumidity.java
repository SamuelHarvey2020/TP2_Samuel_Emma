package com.w32.tp2_samuel_emma.model;

import com.w32.tp2_samuel_emma.sensor.SensorData;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerHumidity {
	public static final double EPSILON = 0.001;
	//Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public ModelControllerHumidity(SensorData p)
    {
		// A COMPLETER
    }


    public void setNbZones(int nbZones)
    {
		// A COMPLETER
    }

    public int getNbZones()
    {
		// A COMPLETER
		return 0;
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
