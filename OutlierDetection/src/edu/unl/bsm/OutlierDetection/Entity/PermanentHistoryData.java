package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the PERMANENT_HISTORY_DATA database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="PERMANENT_HISTORY_DATA")
public class PermanentHistoryData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PermanentHistoryDataPK id;

	@Column(name="HIST_VALUE")
	private float histValue;

	@Column(name="LAST_UPDATED")
	private Timestamp lastUpdated;

	@Column(name="REPORTING_OBJ_ID_BOTTOM")
	private int reportingObjIdBottom;

	@Column(name="REPORTING_OBJ_ID_MIDDLE")
	private int reportingObjIdMiddle;

	@Column(name="REPORTING_OBJ_ID_TOP")
	private int reportingObjIdTop;

	public PermanentHistoryData() {
	}

	public PermanentHistoryDataPK getId() {
		return this.id;
	}

	public void setId(PermanentHistoryDataPK id) {
		this.id = id;
	}

	public float getHistValue() {
		return this.histValue;
	}

	public void setHistValue(float histValue) {
		this.histValue = histValue;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getReportingObjIdBottom() {
		return this.reportingObjIdBottom;
	}

	public void setReportingObjIdBottom(int reportingObjIdBottom) {
		this.reportingObjIdBottom = reportingObjIdBottom;
	}

	public int getReportingObjIdMiddle() {
		return this.reportingObjIdMiddle;
	}

	public void setReportingObjIdMiddle(int reportingObjIdMiddle) {
		this.reportingObjIdMiddle = reportingObjIdMiddle;
	}

	public int getReportingObjIdTop() {
		return this.reportingObjIdTop;
	}

	public void setReportingObjIdTop(int reportingObjIdTop) {
		this.reportingObjIdTop = reportingObjIdTop;
	}

}