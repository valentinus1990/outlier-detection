package edu.unl.bsm.OutlierDetection.Dao;

import java.sql.Timestamp;
import java.util.Date;

public class WeatherDataFormHist {
	private Timestamp dateCollected;
	private float maxTemp;
	private float minTemp;
	private float currentTemp;
	private float relativeHum;
	
	public WeatherDataFormHist(Timestamp dateCollected, float maxTemp, float minTemp, float currentTemp, float relativeHum){
		this.dateCollected = dateCollected;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
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
	 * @return the maxTemp
	 */
	public float getMaxTemp() {
		return maxTemp;
	}
	/**
	 * @param maxTemp the maxTemp to set
	 */
	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}
	/**
	 * @return the minTemp
	 */
	public float getMinTemp() {
		return minTemp;
	}
	/**
	 * @param minTemp the minTemp to set
	 */
	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
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
