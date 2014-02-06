package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the HOURLY_WEATHER database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="HOURLY_WEATHER")
public class HourlyWeather implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="AIR_PRESSURE_IN")
	private float airPressureIn;

	
	@Column(name="DATE_COLLECTED")
	private Timestamp dateCollected;

	@Column(name="DEWPOINT_F")
	private float dewpointF;

	@Column(name="DRY_BULB_F")
	private float dryBulbF;

	@Column(name="RELATIVE_HUMIDITY_PCT")
	private float relativeHumidityPct;

	@Column(name="VISIBILITY_MI")
	private float visibilityMi;

	@Column(name="WEATHER_DESCRIPTION")
	private String weatherDescription;

	@Column(name="WIND_DIRECTION_DEG")
	private float windDirectionDeg;

	@Column(name="WIND_GUST_MPH")
	private float windGustMph;

	@Column(name="WIND_SPEED_MPH")
	private float windSpeedMph;

	public HourlyWeather() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getAirPressureIn() {
		return this.airPressureIn;
	}

	public void setAirPressureIn(float airPressureIn) {
		this.airPressureIn = airPressureIn;
	}

	public Timestamp getDateCollected() {
		return this.dateCollected;
	}

	public void setDateCollected(Timestamp dateCollected) {
		this.dateCollected = dateCollected;
	}

	public float getDewpointF() {
		return this.dewpointF;
	}

	public void setDewpointF(float dewpointF) {
		this.dewpointF = dewpointF;
	}

	public float getDryBulbF() {
		return this.dryBulbF;
	}

	public void setDryBulbF(float dryBulbF) {
		this.dryBulbF = dryBulbF;
	}

	public float getRelativeHumidityPct() {
		return this.relativeHumidityPct;
	}

	public void setRelativeHumidityPct(float relativeHumidityPct) {
		this.relativeHumidityPct = relativeHumidityPct;
	}

	public float getVisibilityMi() {
		return this.visibilityMi;
	}

	public void setVisibilityMi(float visibilityMi) {
		this.visibilityMi = visibilityMi;
	}

	public String getWeatherDescription() {
		return this.weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	public float getWindDirectionDeg() {
		return this.windDirectionDeg;
	}

	public void setWindDirectionDeg(float windDirectionDeg) {
		this.windDirectionDeg = windDirectionDeg;
	}

	public float getWindGustMph() {
		return this.windGustMph;
	}

	public void setWindGustMph(float windGustMph) {
		this.windGustMph = windGustMph;
	}

	public float getWindSpeedMph() {
		return this.windSpeedMph;
	}

	public void setWindSpeedMph(float windSpeedMph) {
		this.windSpeedMph = windSpeedMph;
	}

}