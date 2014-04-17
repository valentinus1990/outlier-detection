package edu.unl.bsm.OutlierDetection.Dao;

import java.sql.Timestamp;
import java.util.Date;

public class WeatherDataFinalForm {
	private Timestamp date;
	private int dayOfWeek;
	private int hourOfDay;
	private float maxTemp;
	private float minTemp;
	private float curTemp;
	private float relativeHum;
	private float coolingLoad;
	
	public WeatherDataFinalForm(Timestamp date, int dayOfWeek,int hourOfDay, float maxTemp, float minTemp, float curTemp, float relativeHum, float coolingLoad){
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.hourOfDay = hourOfDay;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.curTemp = curTemp;
		this.relativeHum = relativeHum;
		this.coolingLoad = coolingLoad;
	}

	public WeatherDataFinalForm(){
		
	}
	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * @return the dayOfWeek
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the hourOfDay
	 */
	public int getHourOfDay() {
		return hourOfDay;
	}

	/**
	 * @param hourOfDay the hourOfDay to set
	 */
	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
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
	 * @return the curTemp
	 */
	public float getCurTemp() {
		return curTemp;
	}

	/**
	 * @param curTemp the curTemp to set
	 */
	public void setCurTemp(float curTemp) {
		this.curTemp = curTemp;
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

	/**
	 * @return the coolingLoad
	 */
	public float getCoolingLoad() {
		return coolingLoad;
	}

	/**
	 * @param coolingLoad the coolingLoad to set
	 */
	public void setCoolingLoad(float coolingLoad) {
		this.coolingLoad = coolingLoad;
	}

}
