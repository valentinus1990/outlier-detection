package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the outlier_detection_output database table.
 * 
 */
@Entity
@Table(name="outlier_detection_output")
@SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
public class OutlierDetectionOutput implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq")
	private int outlIerDetectionId;

	private int outlierDetectionHour;

	private float outlierDetectionMaxCDLOF;

	public OutlierDetectionOutput() {
	}

	public int getOutlIerDetectionId() {
		return this.outlIerDetectionId;
	}

	public void setOutlIerDetectionId(int outlIerDetectionId) {
		this.outlIerDetectionId = outlIerDetectionId;
	}

	public int getOutlierDetectionHour() {
		return this.outlierDetectionHour;
	}

	public void setOutlierDetectionHour(int outlierDetectionHour) {
		this.outlierDetectionHour = outlierDetectionHour;
	}

	public float getOutlierDetectionMaxCDLOF() {
		return this.outlierDetectionMaxCDLOF;
	}

	public void setOutlierDetectionMaxCDLOF(float outlierDetectionMaxCDLOF) {
		this.outlierDetectionMaxCDLOF = outlierDetectionMaxCDLOF;
	}

}