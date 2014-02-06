package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PAST_FORECASTS database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="PAST_FORECASTS")
public class PastForecast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CHANCE_PRECIP_PCT")
	private float chancePrecipPct;

	@Column(name="DEWPOINT_F")
	private float dewpointF;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FORECAST_OBTAINED")
	private Date forecastObtained;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FORECAST_TARGET")
	private Date forecastTarget;

	@Column(name="REL_HUMIDITY_PCT")
	private float relHumidityPct;

	@Column(name="SKY_COVER_PCT")
	private float skyCoverPct;

	@Column(name="TEMPERATURE_F")
	private float temperatureF;

	@Column(name="WIND_DIR")
	private String windDir;

	@Column(name="WIND_SPEED_GUST_MPH")
	private float windSpeedGustMph;

	@Column(name="WIND_SPEED_MPH")
	private float windSpeedMph;

	public PastForecast() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getChancePrecipPct() {
		return this.chancePrecipPct;
	}

	public void setChancePrecipPct(float chancePrecipPct) {
		this.chancePrecipPct = chancePrecipPct;
	}

	public float getDewpointF() {
		return this.dewpointF;
	}

	public void setDewpointF(float dewpointF) {
		this.dewpointF = dewpointF;
	}

	public Date getForecastObtained() {
		return this.forecastObtained;
	}

	public void setForecastObtained(Date forecastObtained) {
		this.forecastObtained = forecastObtained;
	}

	public Date getForecastTarget() {
		return this.forecastTarget;
	}

	public void setForecastTarget(Date forecastTarget) {
		this.forecastTarget = forecastTarget;
	}

	public float getRelHumidityPct() {
		return this.relHumidityPct;
	}

	public void setRelHumidityPct(float relHumidityPct) {
		this.relHumidityPct = relHumidityPct;
	}

	public float getSkyCoverPct() {
		return this.skyCoverPct;
	}

	public void setSkyCoverPct(float skyCoverPct) {
		this.skyCoverPct = skyCoverPct;
	}

	public float getTemperatureF() {
		return this.temperatureF;
	}

	public void setTemperatureF(float temperatureF) {
		this.temperatureF = temperatureF;
	}

	public String getWindDir() {
		return this.windDir;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public float getWindSpeedGustMph() {
		return this.windSpeedGustMph;
	}

	public void setWindSpeedGustMph(float windSpeedGustMph) {
		this.windSpeedGustMph = windSpeedGustMph;
	}

	public float getWindSpeedMph() {
		return this.windSpeedMph;
	}

	public void setWindSpeedMph(float windSpeedMph) {
		this.windSpeedMph = windSpeedMph;
	}

}