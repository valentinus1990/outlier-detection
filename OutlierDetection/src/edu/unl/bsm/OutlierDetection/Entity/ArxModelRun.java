package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import org.joda.time.DateTime;


/**
 * The persistent class for the arx_model_run database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="arx_model_run")
@SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
public class ArxModelRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq")
	@Column(name="ARX_MODEL_RUN_ID")
	private long runId;

	@Column(name="ARX_MODEL_RUN_DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeUpdate;

	@Column(name="ARX_MODEL_RUN_WEEK_DAY")
	private String weekDay;
	

	//bi-directional one to many association to ArxModelResult
	@OneToMany(mappedBy="arxModelRun", cascade={CascadeType.REMOVE})
	private List<ArxModelResult> arxModelResults;

	//bi-directional many-to-one association to ArxModelType
	@ManyToOne
	@JoinColumn(name="ARX_MODEL_TYPE_ID")
	private ArxModelType arxModelType;

	public ArxModelRun() {
	}

	public long getRunId() {
		return this.runId;
	}

	public void setRunId(long runId) {
		this.runId = runId;
	}

	public Date getTimeUpdate() {
		return this.timeUpdate;
	}

	public void setTimeUpdate(Date timeUpdate) {
		this.timeUpdate = timeUpdate;
	}

	public String getWeekDay() {
		return this.weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public List<ArxModelResult> getArxModelResults() {
		return this.arxModelResults;
	}

	public void setArxModelResults(List<ArxModelResult> arxModelResults) {
		this.arxModelResults = arxModelResults;
	}

	public ArxModelResult addArxModelResult(ArxModelResult arxModelResult) {
		getArxModelResults().add(arxModelResult);
		arxModelResult.setArxModelRun(this);

		return arxModelResult;
	}

	public ArxModelResult removeArxModelResult(ArxModelResult arxModelResult) {
		getArxModelResults().remove(arxModelResult);
		arxModelResult.setArxModelRun(null);

		return arxModelResult;
	}

	public ArxModelType getArxModelType() {
		return this.arxModelType;
	}

	public void setArxModelType(ArxModelType arxModelType) {
		this.arxModelType = arxModelType;
	}


}