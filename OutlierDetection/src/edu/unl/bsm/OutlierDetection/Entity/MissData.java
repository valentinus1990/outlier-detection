package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the miss_data database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="miss_data")
@SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
public class MissData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name="MISS_DATA_ID")
	private int missId;

	@Column(name="MISS_DATA_DATETIME")
	private Timestamp missDatetime;

	@Column(name="MISS_DATA_MAX_TEMPERATURE")
	private double missMaxT;

	@Column(name="MISS_DATA_MIN_TEMPERATURE")
	private double missMinT;

	@Column(name="MISS_DATA_TARGET_TEMPERATURE")
	private double miss_RH_t;

	@Column(name="MISS_DATA_RELATIVE_HUMIDITY")
	private double miss_T_t;

	public MissData() {
	}

	public int getMissId() {
		return this.missId;
	}

	public void setMissId(int missId) {
		this.missId = missId;
	}

	public Timestamp getMissDatetime() {
		return this.missDatetime;
	}

	public void setMissDatetime(Timestamp missDatetime) {
		this.missDatetime = missDatetime;
	}

	public double getMissMaxT() {
		return this.missMaxT;
	}

	public void setMissMaxT(double missMaxT) {
		this.missMaxT = missMaxT;
	}

	public double getMissMinT() {
		return this.missMinT;
	}

	public void setMissMinT(double missMinT) {
		this.missMinT = missMinT;
	}

	public double getMiss_RH_t() {
		return this.miss_RH_t;
	}

	public void setMiss_RH_t(double miss_RH_t) {
		this.miss_RH_t = miss_RH_t;
	}

	public double getMiss_T_t() {
		return this.miss_T_t;
	}

	public void setMiss_T_t(double miss_T_t) {
		this.miss_T_t = miss_T_t;
	}

}