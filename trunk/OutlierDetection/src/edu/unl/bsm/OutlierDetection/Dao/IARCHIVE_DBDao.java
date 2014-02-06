package edu.unl.bsm.OutlierDetection.Dao;

import java.text.ParseException;
import java.util.List;

import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;

/**
 * 
 * @author ZhongYin Zhang
 *
 */

public interface IARCHIVE_DBDao {
	
	
	public List<Object[]> getHistData() throws ParseException;
	
	public List<PermanentHistoryData> getPermanentHistCLData();

	public List<Object[]> getRecentData() throws ParseException;
	
	
}
