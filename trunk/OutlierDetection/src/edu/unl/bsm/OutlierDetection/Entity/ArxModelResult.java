package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the arx_model_result database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="ARX_MODEL_RESULT")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
public class ArxModelResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq")
	@Column(name="ARX_MODEL_RESULT_ID")
	private long resultId;

	@Column(name="ARX_MODEL_RESULT_B_INDEX")
	private int bIndex;

	@Column(name="ARX_MODEL_RESULT_B_VALUE")
	private double bValue;
	
	@Column(name = "ARX_MODEL_RESULT_HOUR")
	private Time resultHour;

	//bi-directional many-to-one association to ArxModelRun
	@ManyToOne
	@JoinColumn(name="ARX_MODEL_RUN_ID")
	private ArxModelRun arxModelRun;

	public ArxModelResult() {
	}

	public long getResultId() {
		return this.resultId;
	}

	public void setResultId(long resultId) {
		this.resultId = resultId;
	}

	public int getBIndex() {
		return this.bIndex;
	}

	public void setBIndex(int bIndex) {
		this.bIndex = bIndex;
	}

	public double getBValue() {
		return this.bValue;
	}

	public void setBValue(double bValue) {
		this.bValue = bValue;
	}

	public ArxModelRun getArxModelRun() {
		return this.arxModelRun;
	}

	public void setArxModelRun(ArxModelRun arxModelRun) {
		this.arxModelRun = arxModelRun;
	}

	/**
	 * @return the resultHour
	 */
	public Time getResultHour() {
		return resultHour;
	}

	/**
	 * @param resultHour the resultHour to set
	 */
	public void setResultHour(Time resultHour) {
		this.resultHour = resultHour;
	}

}