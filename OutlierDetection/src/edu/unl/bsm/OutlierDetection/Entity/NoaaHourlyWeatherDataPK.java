package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the NOAA_HOURLY_WEATHER_DATA database table.
 * @author ZhongYin Zhang
 */
@Embeddable
public class NoaaHourlyWeatherDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="READING_ID")
	private int readingId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NOAA_DATE_COLLECTED")
	private java.util.Date noaaDateCollected;

	public NoaaHourlyWeatherDataPK() {
	}
	public int getReadingId() {
		return this.readingId;
	}
	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}
	public java.util.Date getNoaaDateCollected() {
		return this.noaaDateCollected;
	}
	public void setNoaaDateCollected(java.util.Date noaaDateCollected) {
		this.noaaDateCollected = noaaDateCollected;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NoaaHourlyWeatherDataPK)) {
			return false;
		}
		NoaaHourlyWeatherDataPK castOther = (NoaaHourlyWeatherDataPK)other;
		return 
			(this.readingId == castOther.readingId)
			&& this.noaaDateCollected.equals(castOther.noaaDateCollected);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.readingId;
		hash = hash * prime + this.noaaDateCollected.hashCode();
		
		return hash;
	}
}