package edu.unl.bsm.OutlierDetection.FaultTolerant;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import edu.unl.bsm.OutlierDetection.Dao.ARCHIVE_DBDaoImpl;
import edu.unl.bsm.OutlierDetection.Util.Utility;


/**
 * 
 * @author ZhongYin Zhang
 *
 */
public class FaultTolerant {
	
	/**
	 * combine historical missing data
	 * @return
	 * @throws ParseException
	 */
	public List<Timestamp> combineHistMissData() throws ParseException{
		
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<Object[]> histData = dao.getHistData();
		List<Object[]> recentData = dao.getRecentData();
		
		
		List<Timestamp> missData_hist = getMissData(histData);
		List<Timestamp> missData_recent = getMissData(recentData);
		
		Set<Timestamp> temp = new HashSet<Timestamp>();
		
		temp.addAll(missData_hist);
		temp.addAll(missData_recent);
		List<Timestamp> result = new ArrayList<Timestamp>(temp);
		Collections.sort(result);
		if(Utility.DEBUG){
			System.out.println("FautTolerant.java : combineMissingData()");
			for(Timestamp data: result){
			System.out.println(data);
			}
			System.out.println("Miss Data Size: "+ result.size());
		}
		return result;
	}
	
	/**
	 * get Last week missing data
	 * @return
	 * @throws ParseException
	 */
	public List<Timestamp> getLastWeekMissData() throws ParseException{
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<Object[]> lastWeekData = dao.getLastWeekData();
		if(Utility.DEBUG){
			System.out.println("FaultTolerant.java: lastWeekMissData()");
			Utility.printArrayList(lastWeekData);
		}
		
		List<Timestamp> missData_lastWeek = getMissData(lastWeekData);
		Collections.sort(missData_lastWeek);
		if(Utility.DEBUG){
			System.out.println(missData_lastWeek);
		}

		return missData_lastWeek;
	}
	
	/**
	 * calculate the missing data
	 * @param obj
	 * @return
	 */
	public List<Timestamp> getMissData(List<Object[]> obj){
		
		if(obj == null){
			throw new NullPointerException();
		}
		
		Object lastDate = obj.get(obj.size()-1)[0];
		
		Object firstDate = obj.get(0)[0];
		
		DateTime startTime = new DateTime(firstDate);
		
		DateTime endTime = new DateTime(lastDate);
		
		if(Utility.DEBUG){
			System.out.println("FaultTolerant.java: getMissData()");
			System.out.println(startTime);
			System.out.println(endTime);
		}
		
		Hours hours = Hours.hoursBetween(startTime, endTime);
		
		if(Utility.DEBUG){
			System.out.println("FaultTolerant.java: getMissData()");
			System.out.println("Number of Hours: " + (hours.getHours()+1));
			System.out.println("Number of size: " + obj.size());
		}
		
		int h = hours.getHours()+1;
		List<Timestamp> missData = new ArrayList<Timestamp>();
		//if number of hours != number of size traverse the list.
		if(obj.size() != h){
			for(int i = 1, j = 1; i < obj.size()&& j < h; j++){
				
					//check daylight saving changes
//					if(checkDST(obj.get(i)[0])){			
						//CDT
						if(!convertToTimestamp(obj.get(i)[0]).equals(convertToTimestamp(startTime.plusHours(j)))){
							missData.add(new Timestamp(startTime.plusHours(j).getMillis()));
						}else{
							i++;
						}
//					}else{
//						//CST
//						if(!convertToTimestamp(obj.get(i)[0]).equals(convertToTimestamp(startTime.plusHours(j)))){
//							missData.add(new Timestamp(startTime.plusHours(j).getMillis()));
//						}else{
//							i++;
//						}
//						
//					}
			}
		}
		
		return missData;
	}
	
	
	/**
	 * Convert regular object to Timestamp
	 * @param obj
	 * @return
	 */
	public Timestamp convertToTimestamp(Object obj){
		if(obj == null){
			throw new NullPointerException();
		}
		DateTime dateTime =  new DateTime(obj);
		
		return new Timestamp(dateTime.getMillis());
	}
	
	
	/**
	 * check daylight saving changes
	 * @param obj
	 * @return
	 */
	public boolean checkDST(Object obj){
		if(obj == null ){
			throw new NullPointerException();
		}
		DateTime dateTime =  new DateTime(obj);
		
		
		TimeZone tz = TimeZone.getTimeZone(dateTime.getZone().getID());
		boolean checkDST = tz.inDaylightTime(dateTime.toDate());
		return checkDST;
	}
	

}
