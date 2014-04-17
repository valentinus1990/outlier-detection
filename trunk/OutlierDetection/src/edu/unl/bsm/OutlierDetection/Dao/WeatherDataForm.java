package edu.unl.bsm.OutlierDetection.Dao;

import java.sql.Timestamp;

public class WeatherDataForm {
	private Timestamp dateCollected;
	private float currentTemp;
	private float relativeHum;
	
	public WeatherDataForm(Timestamp dateCollected, float currentTemp, float relativeHum){
		this.dateCollected = dateCollected;
		this.currentTemp = currentTemp;
		this.relativeHum = relativeHum;
	}
	/**
	 * @return the dateCollected
	 */
	public Timestamp getDateCollected() {
		return dateCollected;
	}
	/**
	 * @param dateCollected the dateCollected to set
	 */
	public void setDateCollected(Timestamp dateCollected) {
		this.dateCollected = dateCollected;
	}
	/**
	 * @return the currentTemp
	 */
	public float getCurrentTemp() {
		return currentTemp;
	}
	/**
	 * @param currentTemp the currentTemp to set
	 */
	public void setCurrentTemp(float currentTemp) {
		this.currentTemp = currentTemp;
	}
	/**
	 * @return the relativeHum
	 */
	public float getRelativeHum() {
		return relativeHum;
	}
	/**
	 * @param relativeHum the relativeHum to set
	 */
	public void setRelativeHum(float relativeHum) {
		this.relativeHum = relativeHum;
	}
	
	
}
