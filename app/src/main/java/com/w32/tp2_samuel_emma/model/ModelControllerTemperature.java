package com.w32.tp2_samuel_emma.model;

import com.w32.tp2_samuel_emma.sensor.SensorData;

//Complétez l'implémentation de la classe avec les méthodes et attributs nécessaires à son fonctionnement
public class ModelControllerTemperature{
	
	public ModelControllerTemperature(SensorData p)
    {
       
    }
		
	//Les méthodes méthodes suivantes sont TOUTES OBLIGATOIRES et leurs signatures DE DOIVENT PAS être modifiées
    public void setHighLimit(double highLimit)
    {
        
    }
	
	public double getHighLimit()
    {
        // A COMPLETER
		return 0;
    }

    public void setLowLimit(double lowLimit)
    {
    
    }	

    public double getLowLimit()
    {
        // A COMPLETER
		return 0;
    }

    public void onHighLimitUp() 
	{
    

    }

    public void onHighLimitDown() 
	{
    
    }


    public void onLowLimitDown() 
	{
    
    }

    public void onLowLimitUp() 
	{
    
    }  
}
