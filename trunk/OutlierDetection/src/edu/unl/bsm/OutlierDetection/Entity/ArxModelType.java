package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the arx_model_type database table.
 * @author ZhongYin Zhang
 */
@Entity
@Table(name="arx_model_type")
public class ArxModelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ARX_MODEL_TYPE_ID")
	private int typeId;

	@Column(name="ARX_MODEL_TYPE_ARX_NAME")
	private String ARX_name;

	@Column(name="ARX_MODEL_TYPE_DESCRIPTION")
	private String desc;

	//bi-directional many-to-one association to ArxModelRun
	@OneToMany(mappedBy="arxModelType")
	private List<ArxModelRun> arxModelRuns;

	public ArxModelType() {
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getARX_name() {
		return this.ARX_name;
	}

	public void setARX_name(String ARX_name) {
		this.ARX_name = ARX_name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<ArxModelRun> getArxModelRuns() {
		return this.arxModelRuns;
	}

	public void setArxModelRuns(List<ArxModelRun> arxModelRuns) {
		this.arxModelRuns = arxModelRuns;
	}

	public ArxModelRun addArxModelRun(ArxModelRun arxModelRun) {
		getArxModelRuns().add(arxModelRun);
		arxModelRun.setArxModelType(this);

		return arxModelRun;
	}

	public ArxModelRun removeArxModelRun(ArxModelRun arxModelRun) {
		getArxModelRuns().remove(arxModelRun);
		arxModelRun.setArxModelType(null);

		return arxModelRun;
	}

}