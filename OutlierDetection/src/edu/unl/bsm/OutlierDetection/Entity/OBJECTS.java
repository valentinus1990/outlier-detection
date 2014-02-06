package edu.unl.bsm.OutlierDetection.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the OBJECTS database table.
 * @author ZhongYin Zhang
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "getCLPK", query = "SELECT t FROM OBJECTS t "+
									"WHERE " +
									"t.bldg = 'CCUP' AND " +
									"t.system = 'CHW' AND " +
									"t.subsystem = 'CAMPUS' AND " +
									"t.object = 'RATE'"),
		@NamedQuery(name = "getTempPK", query = "SELECT t FROM OBJECTS t " +
									"WHERE " +
									"t.bldg = 'UNL' AND " +
									"t.system = 'CAMPUS' AND " +
									"t.subsystem = 'WEATHER' AND " +
									"t.object = 'HISTORY'"),
		@NamedQuery(name = "getRHPK", query = "SELECT t FROM OBJECTS t " +
									"WHERE " +
									"t.bldg = 'UNL' AND " +
									"t.system = 'CAMPUS' AND " +
									"t.subsystem = 'WEATHER' AND " +
									"t.object = 'HISTORY'")
}
)

public class OBJECTS implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OBJECTSPK id;

	@Column(name="BACNET_OBJECT_IDENTIFIER")
	private int bacnetObjectIdentifier;

	@Lob
	private String biography;

	private String bldg;

	@Column(name="CREATED_DATE_TIME")
	private Timestamp createdDateTime;

	@Column(name="CREATOR_OBJ_ID_BOTTOM")
	private int creatorObjIdBottom;

	@Column(name="CREATOR_OBJ_ID_MIDDLE")
	private int creatorObjIdMiddle;

	@Column(name="CREATOR_OBJ_ID_TOP")
	private int creatorObjIdTop;

	@Column(name="DISP_FMT")
	private String dispFmt;

	@Column(name="DISP_PRI")
	private int dispPri;

	@Column(name="DISP_SEQ")
	private int dispSeq;

	@Column(name="EU_PRIMARY")
	private int euPrimary;

	@Column(name="EU_SECONDARY")
	private int euSecondary;

	@Column(name="EXPANDED_NAME")
	private String expandedName;

	@Column(name="KEY_VAL")
	private int keyVal;

	@Column(name="LOCK_VAL")
	private int lockVal;

	@Column(name="MIN_TREND_INTERVAL")
	private int minTrendInterval;

	@Column(name="MODIFIED_DATE_TIME")
	private Timestamp modifiedDateTime;

	@Column(name="MODIFIER_OBJ_ID_BOTTOM")
	private int modifierObjIdBottom;

	@Column(name="MODIFIER_OBJ_ID_MIDDLE")
	private int modifierObjIdMiddle;

	@Column(name="MODIFIER_OBJ_ID_TOP")
	private int modifierObjIdTop;

	@Column(name="OBJ_TYPE")
	private int objType;

	private String object;

	private String subsystem;

	private String system;

	@Column(name="TREND_INTERVAL")
	private int trendInterval;

	@Column(name="TREND_PURGE_DAYS")
	private int trendPurgeDays;

	@Column(name="VALUE_TOLERANCE")
	private float valueTolerance;

	public OBJECTS() {
	}

	public OBJECTSPK getId() {
		return this.id;
	}

	public void setId(OBJECTSPK id) {
		this.id = id;
	}

	public int getBacnetObjectIdentifier() {
		return this.bacnetObjectIdentifier;
	}

	public void setBacnetObjectIdentifier(int bacnetObjectIdentifier) {
		this.bacnetObjectIdentifier = bacnetObjectIdentifier;
	}

	public String getBiography() {
		return this.biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getBldg() {
		return this.bldg;
	}

	public void setBldg(String bldg) {
		this.bldg = bldg;
	}

	public Timestamp getCreatedDateTime() {
		return this.createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public int getCreatorObjIdBottom() {
		return this.creatorObjIdBottom;
	}

	public void setCreatorObjIdBottom(int creatorObjIdBottom) {
		this.creatorObjIdBottom = creatorObjIdBottom;
	}

	public int getCreatorObjIdMiddle() {
		return this.creatorObjIdMiddle;
	}

	public void setCreatorObjIdMiddle(int creatorObjIdMiddle) {
		this.creatorObjIdMiddle = creatorObjIdMiddle;
	}

	public int getCreatorObjIdTop() {
		return this.creatorObjIdTop;
	}

	public void setCreatorObjIdTop(int creatorObjIdTop) {
		this.creatorObjIdTop = creatorObjIdTop;
	}

	public String getDispFmt() {
		return this.dispFmt;
	}

	public void setDispFmt(String dispFmt) {
		this.dispFmt = dispFmt;
	}

	public int getDispPri() {
		return this.dispPri;
	}

	public void setDispPri(int dispPri) {
		this.dispPri = dispPri;
	}

	public int getDispSeq() {
		return this.dispSeq;
	}

	public void setDispSeq(int dispSeq) {
		this.dispSeq = dispSeq;
	}

	public int getEuPrimary() {
		return this.euPrimary;
	}

	public void setEuPrimary(int euPrimary) {
		this.euPrimary = euPrimary;
	}

	public int getEuSecondary() {
		return this.euSecondary;
	}

	public void setEuSecondary(int euSecondary) {
		this.euSecondary = euSecondary;
	}

	public String getExpandedName() {
		return this.expandedName;
	}

	public void setExpandedName(String expandedName) {
		this.expandedName = expandedName;
	}

	public int getKeyVal() {
		return this.keyVal;
	}

	public void setKeyVal(int keyVal) {
		this.keyVal = keyVal;
	}

	public int getLockVal() {
		return this.lockVal;
	}

	public void setLockVal(int lockVal) {
		this.lockVal = lockVal;
	}

	public int getMinTrendInterval() {
		return this.minTrendInterval;
	}

	public void setMinTrendInterval(int minTrendInterval) {
		this.minTrendInterval = minTrendInterval;
	}

	public Timestamp getModifiedDateTime() {
		return this.modifiedDateTime;
	}

	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public int getModifierObjIdBottom() {
		return this.modifierObjIdBottom;
	}

	public void setModifierObjIdBottom(int modifierObjIdBottom) {
		this.modifierObjIdBottom = modifierObjIdBottom;
	}

	public int getModifierObjIdMiddle() {
		return this.modifierObjIdMiddle;
	}

	public void setModifierObjIdMiddle(int modifierObjIdMiddle) {
		this.modifierObjIdMiddle = modifierObjIdMiddle;
	}

	public int getModifierObjIdTop() {
		return this.modifierObjIdTop;
	}

	public void setModifierObjIdTop(int modifierObjIdTop) {
		this.modifierObjIdTop = modifierObjIdTop;
	}

	public int getObjType() {
		return this.objType;
	}

	public void setObjType(int objType) {
		this.objType = objType;
	}

	public String getObject() {
		return this.object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getSubsystem() {
		return this.subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public int getTrendInterval() {
		return this.trendInterval;
	}

	public void setTrendInterval(int trendInterval) {
		this.trendInterval = trendInterval;
	}

	public int getTrendPurgeDays() {
		return this.trendPurgeDays;
	}

	public void setTrendPurgeDays(int trendPurgeDays) {
		this.trendPurgeDays = trendPurgeDays;
	}

	public float getValueTolerance() {
		return this.valueTolerance;
	}

	public void setValueTolerance(float valueTolerance) {
		this.valueTolerance = valueTolerance;
	}

}