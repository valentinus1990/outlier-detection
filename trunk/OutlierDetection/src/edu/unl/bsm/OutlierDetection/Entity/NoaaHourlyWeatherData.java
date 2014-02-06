package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the NOAA_HOURLY_WEATHER_DATA database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="NOAA_HOURLY_WEATHER_DATA")
public class NoaaHourlyWeatherData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NoaaHourlyWeatherDataPK id;

	@Column(name="ABSOLUTE_HUMIDITY")
	private BigDecimal absoluteHumidity;

	private BigDecimal altimeter;

	@Column(name="DEWPOINT_C")
	private BigDecimal dewpointC;

	@Column(name="DEWPOINT_F")
	private BigDecimal dewpointF;

	@Column(name="DRY_BULB_C")
	private BigDecimal dryBulbC;

	@Column(name="DRY_BULB_F")
	private BigDecimal dryBulbF;

	
	@Column(name="NOAA_DATE_MODIFIED")
	private Timestamp noaaDateModified;

	@Column(name="PRESSURE_CHANGE")
	private int pressureChange;

	@Column(name="PRESSURE_TENDENCY")
	private int pressureTendency;

	@Column(name="RELATIVE_HUMIDITY")
	private BigDecimal relativeHumidity;

	@Column(name="SEA_LEVEL_PRESSURE")
	private BigDecimal seaLevelPressure;

	@Column(name="SKY_CONDITION")
	private String skyCondition;

	@Column(name="STATION_PRESSURE")
	private BigDecimal stationPressure;

	private int visibility;

	@Column(name="WEATHER_STATION")
	private String weatherStation;

	@Column(name="WEATHER_TYPE")
	private String weatherType;

	@Column(name="WET_BULB_C")
	private BigDecimal wetBulbC;

	@Column(name="WET_BULB_F")
	private BigDecimal wetBulbF;

	@Column(name="WIND_CHARACTER")
	private int windCharacter;

	@Column(name="WIND_DIRECTION")
	private int windDirection;

	@Column(name="WIND_SPEED")
	private BigDecimal windSpeed;
	
	

	public NoaaHourlyWeatherData() {
	}

	public NoaaHourlyWeatherDataPK getId() {
		return this.id;
	}

	public void setId(NoaaHourlyWeatherDataPK id) {
		this.id = id;
	}

	public BigDecimal getAbsoluteHumidity() {
		return this.absoluteHumidity;
	}

	public void setAbsoluteHumidity(BigDecimal absoluteHumidity) {
		this.absoluteHumidity = absoluteHumidity;
	}

	public BigDecimal getAltimeter() {
		return this.altimeter;
	}

	public void setAltimeter(BigDecimal altimeter) {
		this.altimeter = altimeter;
	}

	public BigDecimal getDewpointC() {
		return this.dewpointC;
	}

	public void setDewpointC(BigDecimal dewpointC) {
		this.dewpointC = dewpointC;
	}

	public BigDecimal getDewpointF() {
		return this.dewpointF;
	}

	public void setDewpointF(BigDecimal dewpointF) {
		this.dewpointF = dewpointF;
	}

	public BigDecimal getDryBulbC() {
		return this.dryBulbC;
	}

	public void setDryBulbC(BigDecimal dryBulbC) {
		this.dryBulbC = dryBulbC;
	}

	public BigDecimal getDryBulbF() {
		return this.dryBulbF;
	}

	public void setDryBulbF(BigDecimal dryBulbF) {
		this.dryBulbF = dryBulbF;
	}

	public Timestamp getNoaaDateModified() {
		return this.noaaDateModified;
	}

	public void setNoaaDateModified(Timestamp noaaDateModified) {
		this.noaaDateModified = noaaDateModified;
	}

	public int getPressureChange() {
		return this.pressureChange;
	}

	public void setPressureChange(int pressureChange) {
		this.pressureChange = pressureChange;
	}

	public int getPressureTendency() {
		return this.pressureTendency;
	}

	public void setPressureTendency(int pressureTendency) {
		this.pressureTendency = pressureTendency;
	}

	public BigDecimal getRelativeHumidity() {
		return this.relativeHumidity;
	}

	public void setRelativeHumidity(BigDecimal relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

	public BigDecimal getSeaLevelPressure() {
		return this.seaLevelPressure;
	}

	public void setSeaLevelPressure(BigDecimal seaLevelPressure) {
		this.seaLevelPressure = seaLevelPressure;
	}

	public String getSkyCondition() {
		return this.skyCondition;
	}

	public void setSkyCondition(String skyCondition) {
		this.skyCondition = skyCondition;
	}

	public BigDecimal getStationPressure() {
		return this.stationPressure;
	}

	public void setStationPressure(BigDecimal stationPressure) {
		this.stationPressure = stationPressure;
	}

	public int getVisibility() {
		return this.visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getWeatherStation() {
		return this.weatherStation;
	}

	public void setWeatherStation(String weatherStation) {
		this.weatherStation = weatherStation;
	}

	public String getWeatherType() {
		return this.weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	public BigDecimal getWetBulbC() {
		return this.wetBulbC;
	}

	public void setWetBulbC(BigDecimal wetBulbC) {
		this.wetBulbC = wetBulbC;
	}

	public BigDecimal getWetBulbF() {
		return this.wetBulbF;
	}

	public void setWetBulbF(BigDecimal wetBulbF) {
		this.wetBulbF = wetBulbF;
	}

	public int getWindCharacter() {
		return this.windCharacter;
	}

	public void setWindCharacter(int windCharacter) {
		this.windCharacter = windCharacter;
	}

	public int getWindDirection() {
		return this.windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public BigDecimal getWindSpeed() {
		return this.windSpeed;
	}

	public void setWindSpeed(BigDecimal windSpeed) {
		this.windSpeed = windSpeed;
	}

}