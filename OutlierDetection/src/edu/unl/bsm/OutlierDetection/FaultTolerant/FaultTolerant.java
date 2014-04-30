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
import edu.unl.bsm.OutlierDetection.Dao.WeatherDataForm;
import edu.unl.bsm.OutlierDetection.Util.Utility;


/**
 * 
 * @author ZhongYin Zhang
 *
 */
public class FaultTolerant {
	
	/**
	 * combine historical missing date
	 * @return
	 * @throws ParseException
	 */
	public List<Timestamp> collectHistMissDate() throws ParseException{
		
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<WeatherDataForm> weatherData = dao.getWeatherFormData();
		
		List<Timestamp> missDate = getMissDate(weatherData);
		Set<Timestamp> temp = new HashSet<Timestamp>(missDate);
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
	 * get Last week missing date
	 * @return
	 * @throws ParseException
	 */
	public List<Timestamp> getLastWeekMissDate() throws ParseException{
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<WeatherDataForm> lastWeekData = dao.getLastWeekData();
		if(Utility.DEBUG){
			System.out.println("FaultTolerant.java: lastWeekMissData()");
//			Utility.printArrayList(lastWeekData);
		}
		
		List<Timestamp> missDate_lastWeek = getMissDate(lastWeekData);
		Collections.sort(missDate_lastWeek);
		if(Utility.DEBUG){
			System.out.println(missDate_lastWeek);
		}
		return missDate_lastWeek;
	}
	
	/**
	 * calculate the missing date
	 * @param obj
	 * @return
	 */
	public List<Timestamp> getMissDate(List<WeatherDataForm> weatherData){
		
		if(weatherData == null){
			throw new IllegalArgumentException();
		}
		
		Timestamp lastDate = weatherData.get(weatherData.size() - 1).getDateCollected();
		Timestamp firstDate = weatherData.get(0).getDateCollected();
		
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
			System.out.println("Number of size: " + weatherData.size());
		}
		
		int h = hours.getHours()+1;
		List<Timestamp> missDate = new ArrayList<Timestamp>();
		//if number of hours != number of size of the list traversal.
		if(weatherData.size() != h){
			for(int i = 1, j = 1; i < weatherData.size()&& j < h; j++){
				
					//check daylight saving changes
//					if(checkDST(obj.get(i)[0])){			
						//CDT
						if(!convertToTimestamp(weatherData.get(i).getDateCollected()).equals(convertToTimestamp(startTime.plusHours(j)))){
							missDate.add(new Timestamp(startTime.plusHours(j).getMillis()));
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
		
		return missDate;
	}
	
	
	/**
	 * Convert regular object to Timestamp
	 * @param obj
	 * @return
	 */
	public Timestamp convertToTimestamp(Object obj){
		if(obj == null){
			throw new IllegalArgumentException();
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
