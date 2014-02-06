package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the PERMANENT_HISTORY_DATA database table.
 * @author ZhongYin Zhang
 */
@Embeddable
public class PermanentHistoryDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OBJ_ID_TOP")
	private int objIdTop;

	@Column(name="OBJ_ID_MIDDLE")
	private int objIdMiddle;

	@Column(name="OBJ_ID_BOTTOM")
	private int objIdBottom;

	
	@Column(name="HIST_DATE_TIME")
	private Timestamp histDateTime;

	@Column(name="HIST_STRING")
	private String histString;

	public PermanentHistoryDataPK() {
	}
	public int getObjIdTop() {
		return this.objIdTop;
	}
	public void setObjIdTop(int objIdTop) {
		this.objIdTop = objIdTop;
	}
	public int getObjIdMiddle() {
		return this.objIdMiddle;
	}
	public void setObjIdMiddle(int objIdMiddle) {
		this.objIdMiddle = objIdMiddle;
	}
	public int getObjIdBottom() {
		return this.objIdBottom;
	}
	public void setObjIdBottom(int objIdBottom) {
		this.objIdBottom = objIdBottom;
	}
	public Timestamp getHistDateTime() {
		return this.histDateTime;
	}
	public void setHistDateTime(Timestamp histDateTime) {
		this.histDateTime = histDateTime;
	}
	public String getHistString() {
		return this.histString;
	}
	public void setHistString(String histString) {
		this.histString = histString;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PermanentHistoryDataPK)) {
			return false;
		}
		PermanentHistoryDataPK castOther = (PermanentHistoryDataPK)other;
		return 
			(this.objIdTop == castOther.objIdTop)
			&& (this.objIdMiddle == castOther.objIdMiddle)
			&& (this.objIdBottom == castOther.objIdBottom)
			&& this.histDateTime.equals(castOther.histDateTime)
			&& this.histString.equals(castOther.histString);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.objIdTop;
		hash = hash * prime + this.objIdMiddle;
		hash = hash * prime + this.objIdBottom;
		hash = hash * prime + this.histDateTime.hashCode();
		hash = hash * prime + this.histString.hashCode();
		
		return hash;
	}
}