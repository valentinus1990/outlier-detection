package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DAILY_WEATHER_SUMMARY database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="DAILY_WEATHER_SUMMARY")
public class DailyWeatherSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="AVERAGE_TEMPERATURE")
	private float averageTemperature;

	@Column(name="COOLING_DEGREE_HOURS")
	private float coolingDegreeHours;

	@Temporal(TemporalType.DATE)
	private Date date;
	
	

	@Column(name="HEATING_DEGREE_HOURS")
	private float heatingDegreeHours;

	@Column(name="MAX_TEMPERATURE")
	private float maxTemperature;

	@Column(name="MIN_TEMPERATURE")
	private float minTemperature;

	public DailyWeatherSummary() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getAverageTemperature() {
		return this.averageTemperature;
	}

	public void setAverageTemperature(float averageTemperature) {
		this.averageTemperature = averageTemperature;
	}

	public float getCoolingDegreeHours() {
		return this.coolingDegreeHours;
	}

	public void setCoolingDegreeHours(float coolingDegreeHours) {
		this.coolingDegreeHours = coolingDegreeHours;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getHeatingDegreeHours() {
		return this.heatingDegreeHours;
	}

	public void setHeatingDegreeHours(float heatingDegreeHours) {
		this.heatingDegreeHours = heatingDegreeHours;
	}

	public float getMaxTemperature() {
		return this.maxTemperature;
	}

	public void setMaxTemperature(float maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public float getMinTemperature() {
		return this.minTemperature;
	}

	public void setMinTemperature(float minTemperature) {
		this.minTemperature = minTemperature;
	}

	

}