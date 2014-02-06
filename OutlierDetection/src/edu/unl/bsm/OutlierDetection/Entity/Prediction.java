package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the prediction database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="prediction")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
public class Prediction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="seq")
	@Column(name="prediction_id")
	private int id;

	@Column(name="prediction_cooling_load_value")
	private double preClValue;

	@Column(name="prediction_collect_time")
	private Timestamp preCollectTime;

	@Column(name="prediction_target_time")
	private Timestamp preTargetTime;

	@Column(name = "prediction_arx_type")
	private int arxType;
	
	@Column(name = "prediction_robust_mean_square_error")
	private double preRobustMSE;
	
	public Prediction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPreClValue() {
		return this.preClValue;
	}

	public void setPreClValue(double preClValue) {
		this.preClValue = preClValue;
	}

	public Timestamp getPreCollectTime() {
		return this.preCollectTime;
	}

	public void setPreCollectTime(Timestamp preCollectTime) {
		this.preCollectTime = preCollectTime;
	}

	public Timestamp getPreTargetTime() {
		return this.preTargetTime;
	}

	public void setPreTargetTime(Timestamp preTargetTime) {
		this.preTargetTime = preTargetTime;
	}

	/**
	 * @return the arxType
	 */
	public int getArxType() {
		return arxType;
	}

	/**
	 * @param arxType the arxType to set
	 */
	public void setArxType(int arxType) {
		this.arxType = arxType;
	}

	/**
	 * @return the preRobustMSE
	 */
	public double getPreRobustMSE() {
		return preRobustMSE;
	}

	/**
	 * @param preRobustMSE the preRobustMSE to set
	 */
	public void setPreRobustMSE(double preRobustMSE) {
		this.preRobustMSE = preRobustMSE;
	}

}