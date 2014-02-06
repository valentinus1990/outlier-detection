package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the robust_mean_squared_error database table.
 * 
 */
@Entity
@Table(name="robust_mean_squared_error")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
public class RobustMeanSquaredError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq")
	@Column(name="ROBUST_MEAN_SQUARED_ERROR_ID")
	private int robustMeanSquaredErrorId;

	@Column(name="ROBUST_MEAN_SQUARED_ERROR_ARX_MODEL_TYPE_ID")
	private int robustMeanSquaredErrorArxModelTypeId;

	@Column(name="ROBUST_MEAN_SQUARED_ERROR_TIME_UPDATE")
	private Timestamp robustMeanSquaredErrorTimeUpdate;

	@Column(name="ROBUST_MEAN_SQUARED_ERROR_VALUE")
	private double robustMeanSquaredErrorValue;

	public RobustMeanSquaredError() {
	}

	public int getRobustMeanSquaredErrorId() {
		return this.robustMeanSquaredErrorId;
	}

	public void setRobustMeanSquaredErrorId(int robustMeanSquaredErrorId) {
		this.robustMeanSquaredErrorId = robustMeanSquaredErrorId;
	}

	public int getRobustMeanSquaredErrorArxModelTypeId() {
		return this.robustMeanSquaredErrorArxModelTypeId;
	}

	public void setRobustMeanSquaredErrorArxModelTypeId(int robustMeanSquaredErrorArxModelTypeId) {
		this.robustMeanSquaredErrorArxModelTypeId = robustMeanSquaredErrorArxModelTypeId;
	}

	public Timestamp getRobustMeanSquaredErrorTimeUpdate() {
		return this.robustMeanSquaredErrorTimeUpdate;
	}

	public void setRobustMeanSquaredErrorTimeUpdate(Timestamp robustMeanSquaredErrorTimeUpdate) {
		this.robustMeanSquaredErrorTimeUpdate = robustMeanSquaredErrorTimeUpdate;
	}

	public double getRobustMeanSquaredErrorValue() {
		return this.robustMeanSquaredErrorValue;
	}

	public void setRobustMeanSquaredErrorValue(double robustMeanSquaredErrorValue) {
		this.robustMeanSquaredErrorValue = robustMeanSquaredErrorValue;
	}

}