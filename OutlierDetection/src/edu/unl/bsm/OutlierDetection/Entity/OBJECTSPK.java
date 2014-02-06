package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the OBJECTS database table.
 * @author ZhongYin Zhang
 */
@Embeddable
public class OBJECTSPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OBJ_ID_TOP")
	private int objIdTop;

	@Column(name="OBJ_ID_MIDDLE")
	private int objIdMiddle;

	@Column(name="OBJ_ID_BOTTOM")
	private int objIdBottom;

	public OBJECTSPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OBJECTSPK)) {
			return false;
		}
		OBJECTSPK castOther = (OBJECTSPK)other;
		return 
			(this.objIdTop == castOther.objIdTop)
			&& (this.objIdMiddle == castOther.objIdMiddle)
			&& (this.objIdBottom == castOther.objIdBottom);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.objIdTop;
		hash = hash * prime + this.objIdMiddle;
		hash = hash * prime + this.objIdBottom;
		
		return hash;
	}
}