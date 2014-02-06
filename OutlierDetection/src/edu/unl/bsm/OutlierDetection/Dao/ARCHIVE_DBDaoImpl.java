package edu.unl.bsm.OutlierDetection.Dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Entity.OBJECTS;
import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;
import edu.unl.bsm.OutlierDetection.Util.Utility;

/**
 * @author ZhongYin Zhang
 * 
 */
public class ARCHIVE_DBDaoImpl implements IARCHIVE_DBDao {

	private static final String PERSISTENCE_UNIT_NAME = "ARCHIVE_DB";
	private static EntityManagerFactory factory;
	private EntityManager em;

	public ARCHIVE_DBDaoImpl() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}

	public EntityManager getEntityManager(){
		return this.em;
	}
	/**
	 * Retrieve data from today - 3 years to 2012-06-28
	 * @throws ParseException 
	 */
	@Override
	public List<Object[]> getHistData() throws ParseException {
		Query query = em.createNativeQuery("SELECT NHWD.NOAA_DATE_COLLECTED, "
//				+ "							DAYOFWEEK(DWS.DATE)-1 AS dayofweek, "
//				+ "							HOUR(NHWD.NOAA_DATE_COLLECTED) AS hour, "
				+ "							DWS.MAX_TEMPERATURE AS max_t, "
				+ "							DWS.MIN_TEMPERATURE AS  min_t, "
				+ "							NHWD.DRY_BULB_F AS T_t, " 
				+ "							NHWD.RELATIVE_HUMIDITY AS RH_t " 
				+ "							FROM "
				+ "							NOAA_HOURLY_WEATHER_DATA AS NHWD " + "INNER JOIN "
				+ "							DAILY_WEATHER_SUMMARY AS DWS " + "ON "
				+ "							CAST(NHWD.NOAA_DATE_COLLECTED AS DATE) = DWS.DATE "
				+ "							WHERE " 
				+ "							DWS.DATE > CURRENT_DATE() - INTERVAL 3 YEAR AND  NHWD.NOAA_DATE_COLLECTED < CURRENT_DATE() - INTERVAL 10 MINUTE " 
				+ "							ORDER BY NHWD.NOAA_DATE_COLLECTED;");
		
		List<Object[]> result = query.getResultList();
		
		List<Object[]> newResult = new ArrayList<Object[]>();

		Set<Timestamp> filter = new HashSet<Timestamp>();

		result = dateConvert(result);

		for (Object[] element : result) {
			if (!filter.contains((Timestamp)element[0])) {
				filter.add((Timestamp) element[0]);
				newResult.add(element);
			}else{
				System.out.println(element[0]);
			}
		}

		Collections.sort(newResult, new SortByDate());
		return newResult;
	}
	
	

	/**
	 * get the latest recored cooling load data
	 * @return
	 */
	public float getCurrentRecordedCLData(){
		GREEDODaoImpl greedo = new GREEDODaoImpl();
		OBJECTS pk = greedo.getCLPK();

		int objIdTop = pk.getId().getObjIdTop();
		int objIdMiddle = pk.getId().getObjIdMiddle();
		int objIdBottom = pk.getId().getObjIdBottom();
		
		DateTime date = new DateTime();
		DateTime threeYearsEarly = date.minusYears(3);
		Timestamp startDate = new Timestamp(threeYearsEarly.getMillis()); 

		Query query = em.createQuery("SELECT t FROM PermanentHistoryData t "
				+ "WHERE " + "t.id.objIdTop = :objIdTop AND "
				+ "t.id.objIdMiddle = :objIdMiddle AND "
				+ "t.id.objIdBottom = :objIdBottom AND "
				+ "t.id.histDateTime > :startDate " + "ORDER BY "
				+ "t.id.histDateTime DESC");

		query.setParameter("objIdTop", objIdTop);
		query.setParameter("objIdMiddle", objIdMiddle);
		query.setParameter("objIdBottom", objIdBottom);
		query.setParameter("startDate", startDate);

		List<PermanentHistoryData> result = query.getResultList();

		if(Utility.DEBUG){
			 Utility.printArrayListCL(result);
		}
		
		float currentCL = result.get(0).getHistValue();
		return currentCL;
	}
	
	
	/**
	 * Retrieve permanent Historical Cooling Load Data
	 */
	public List<PermanentHistoryData> getPermanentHistCLData() {
		GREEDODaoImpl greedo = new GREEDODaoImpl();
		OBJECTS pk = greedo.getCLPK();

		int objIdTop = pk.getId().getObjIdTop();
		int objIdMiddle = pk.getId().getObjIdMiddle();
		int objIdBottom = pk.getId().getObjIdBottom();
		
		DateTime date = new DateTime();
		DateTime threeYearsEarly = date.minusYears(3);
		Timestamp startDate = new Timestamp(threeYearsEarly.getMillis()); 

		Query query = em.createQuery("SELECT t FROM PermanentHistoryData t "
				+ "WHERE " + "t.id.objIdTop = :objIdTop AND "
				+ "t.id.objIdMiddle = :objIdMiddle AND "
				+ "t.id.objIdBottom = :objIdBottom AND "
				+ "t.id.histDateTime > :startDate " + "ORDER BY "
				+ "t.id.histDateTime");

		query.setParameter("objIdTop", objIdTop);
		query.setParameter("objIdMiddle", objIdMiddle);
		query.setParameter("objIdBottom", objIdBottom);
		query.setParameter("startDate", startDate);

		List<PermanentHistoryData> result = query.getResultList();

		if(Utility.DEBUG){
			 Utility.printArrayListCL(result);
		}
		

		return result;
	}

	
	/**
	 * Retrieve temperature data from PermanentHistoryData table
	 */
	public List<PermanentHistoryData> getPermanentHistTempData(Timestamp timestamp){
		GREEDODaoImpl greedo = new GREEDODaoImpl();
		OBJECTS pk = greedo.getTempPK();
		
		int objIdTop = pk.getId().getObjIdTop();
		int objIdMiddle = pk.getId().getObjIdMiddle();
		int objIdBottom = pk.getId().getObjIdBottom();
		
		String histString = "weather sta tp [F]";

		Query query = em.createQuery("SELECT t FROM PermanentHistoryData t "
				+ "WHERE " + "t.id.objIdTop = :objIdTop AND "
				+ "t.id.objIdMiddle = :objIdMiddle AND "
				+ "t.id.objIdBottom = :objIdBottom AND "
				+ "t.id.histString = :histString AND "
				+ "t.id.histDateTime = :dateTime " + "ORDER BY "
				+ "t.id.histDateTime");

		query.setParameter("objIdTop", objIdTop);
		query.setParameter("objIdMiddle", objIdMiddle);
		query.setParameter("objIdBottom", objIdBottom);
		query.setParameter("dateTime", timestamp);
		query.setParameter("histString", histString);

		List<PermanentHistoryData> result = query.getResultList();

		if(Utility.DEBUG){
			Utility.printArrayListCL(result);
		}

		return result;
	}
	
	
	
	
	/**
	 * Retrieve RH data from PermanentHistoryData table
	 */
	public List<PermanentHistoryData> getPermanentHistRHData(Timestamp timestamp){
		GREEDODaoImpl greedo = new GREEDODaoImpl();
		OBJECTS pk = greedo.getRHPK();
		
		

		int objIdTop = pk.getId().getObjIdTop();
		int objIdMiddle = pk.getId().getObjIdMiddle();
		int objIdBottom = pk.getId().getObjIdBottom();
		
		String histString = "weather sta rh [%]";

		Query query = em.createQuery("SELECT t FROM PermanentHistoryData t "
				+ "WHERE " + "t.id.objIdTop = :objIdTop AND "
				+ "t.id.objIdMiddle = :objIdMiddle AND "
				+ "t.id.objIdBottom = :objIdBottom AND "
				+ "t.id.histString = :histString AND "
				+ "t.id.histDateTime = :dateTime " + "ORDER BY "
				+ "t.id.histDateTime");

		query.setParameter("objIdTop", objIdTop);
		query.setParameter("objIdMiddle", objIdMiddle);
		query.setParameter("objIdBottom", objIdBottom);
		query.setParameter("dateTime", timestamp);
		query.setParameter("histString", histString);

		List<PermanentHistoryData> result = query.getResultList();

		if(Utility.DEBUG){
			Utility.printArrayListCL(result);
		}

		return result;
	}
	
	/**
	 * Retrieve Data after 2012-06-26 upto yesterday 23:00:00
	 * 
	 * @throws ParseException
	 */
	@Override
	public List<Object[]> getRecentData() throws ParseException {
		Query query = em.createNativeQuery("SELECT HW.DATE_COLLECTED, "
				+
				// "DAYOFWEEK(DWS.DATE)-1 AS dayofweek, " +
				// "HOUR(HW.DATE_COLLECTED) AS hour, " +
				"DWS.MAX_TEMPERATURE AS max_t, "
				+ "DWS.MIN_TEMPERATURE AS  min_t, " + "HW.DRY_BULB_F AS T_t, "
				+ "HW.RELATIVE_HUMIDITY_PCT AS RH_t " + "FROM "
				+ "HOURLY_WEATHER AS HW " + "INNER JOIN "
				+ "DAILY_WEATHER_SUMMARY AS DWS " + "ON "
				+ "CAST(HW.DATE_COLLECTED AS DATE) = DWS.DATE " + "WHERE "
				+ "HW.DATE_COLLECTED >= '2012-6-26' AND "
				+ "HW.DATE_COLLECTED < CURRENT_DATE() - INTERVAL 10 MINUTE "
				+ "ORDER BY HW.DATE_COLLECTED ");

		List<Object[]> result = query.getResultList();
		result = dataProcess(result);
		List<Object[]> newResult = new ArrayList<Object[]>();
		Set<Timestamp> filter = new HashSet<Timestamp>();
		result = dateConvert(result);
		for (Object[] element : result) {
			if (!filter.contains((Timestamp)element[0])) {
				filter.add((Timestamp) element[0]);
				newResult.add(element);
			}else{
				
//				System.out.println("Duplicated Date: "+ element[0]);
			}
		}
		Collections.sort(newResult, new SortByDate());
		return newResult;
	}
	
	/**
	 * Retrieve Data one week ago
	 * 
	 * @throws ParseException
	 */
	
	public List<Object[]> getLastWeekData() throws ParseException {
		Query query = em.createNativeQuery("SELECT HW.DATE_COLLECTED, "
				+
				// "DAYOFWEEK(DWS.DATE)-1 AS dayofweek, " +
				// "HOUR(HW.DATE_COLLECTED) AS hour, " +
				"DWS.MAX_TEMPERATURE AS max_t, "
				+ "DWS.MIN_TEMPERATURE AS  min_t, " + "HW.DRY_BULB_F AS T_t, "
				+ "HW.RELATIVE_HUMIDITY_PCT AS RH_t " + "FROM "
				+ "HOURLY_WEATHER AS HW " + "INNER JOIN "
				+ "DAILY_WEATHER_SUMMARY AS DWS " + "ON "
				+ "CAST(HW.DATE_COLLECTED AS DATE) = DWS.DATE " + "WHERE "
				+ "HW.DATE_COLLECTED >= CURRENT_DATE() - INTERVAL 2 WEEK AND "
				+ "HW.DATE_COLLECTED < CURRENT_DATE() - INTERVAL 10 MINUTE "
				+ "ORDER BY HW.DATE_COLLECTED ");

		List<Object[]> result = query.getResultList();
		result = dataProcess(result);
		List<Object[]> newResult = new ArrayList<Object[]>();
		Set<Timestamp> filter = new HashSet<Timestamp>();
		result = dateConvert(result);
		for (Object[] element : result) {
			if (!filter.contains((Timestamp)element[0])) {
				filter.add((Timestamp) element[0]);
				newResult.add(element);
			}else{
//				System.out.println("Duplicated Date: "+ element[0]);
			}
		}
		Collections.sort(newResult, new SortByDate());
		return newResult;
	}
	

	/**
	 * Get the prediction data after current hour until 23:00 today
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> getForecastTodayData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE Hour(FORECAST_TARGET) > Hour(NOW()) AND "
						+ "							date(FORECAST_TARGET) = CURRENT_DATE AND date(FORECAST_OBTAINED) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC");
		List<Object[]> result = query.getResultList();

		List<Object[]> newResult = new ArrayList<Object[]>();

		Set<Date> filter = new HashSet<Date>();

		result = dateConvert(result);

		for (Object[] element : result) {
			if (!filter.contains(element[0])) {
				filter.add((Date) element[0]);
				newResult.add(element);
			}
			
		}

		Collections.sort(newResult, new SortByDate());
//		Utility.printArrayList(newResult);
		return newResult;
	}

	class SortByDate implements Comparator<Object[]> {

		@Override
		public int compare(Object[] o1, Object[] o2) {
			return ((Timestamp) o1[0]).compareTo((Timestamp) o2[0]);
		}
	}
	
	/**
	 * get next 24 hours temperature and Relative humidity
	 * @return
	 * @throws ParseException
	 */
	
	public List<Object[]> getForcastNext24Hours() throws ParseException{
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE FORECAST_TARGET> NOW() AND FORECAST_TARGET < NOW() + INTERVAL 49 HOUR AND " +
						"							date(FORECAST_OBTAINED) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC");
		List<Object[]> result = query.getResultList();

		List<Object[]> newResult = new ArrayList<Object[]>();

		Set<Date> filter = new HashSet<Date>();

		result = dateConvert(result);

		for (Object[] element : result) {
			if (!filter.contains(element[0])) {
				filter.add((Date) element[0]);
				newResult.add(element);
			}
		}
		
//		System.out.println(newResult.size());

		Collections.sort(newResult, new SortByDate());
//		Utility.printArrayList(newResult);
		
		
		return newResult;
		
		
	}

	/**
	 * Get todays data between before last hour
	 * 
	 * @return
	 * @throws ParseException
	 */

	public List<Object[]> getTodaysEarlyData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT DATE_COLLECTED, DRY_BULB_F AS T_t, RELATIVE_HUMIDITY_PCT AS RH_t FROM HOURLY_WEATHER "
						+ "							WHERE DATE_COLLECTED > CURRENT_DATE()-  INTERVAL 10 MINUTE ");

		List<Object[]> result = query.getResultList();
		result = dateConvert(result);
		// Utility.printArrayList(result);

		return result;
	}

	/**
	 * get the data information just for current hour
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> getCurrentHourData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE Hour(FORECAST_TARGET) = Hour(NOW()) AND "
						+ "							date(FORECAST_OBTAINED) = CURRENT_DATE AND DATE(FORECAST_TARGET) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC LIMIT 0 , 1");

		List<Object[]> result = query.getResultList();

		result = dateConvert(result);
		// Utility.printArrayList(result);

		return result;
	}

	/**
	 * Process the data retrieved from database to a regular data format
	 * eg: 13:31:24 --> 14:00:00
	 * 	   13:29:11 --
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public List<Object[]> dataProcess(List<Object[]> data)
			throws ParseException {
		// long previousHour = -1L;
		// Set<Integer> removedIndex = new HashSet<Integer>();

		for (Object[] row : data) {
			Date jdkdate = (Date) row[0];
			
			DateTime jodaDate = new DateTime(jdkdate);
			
			int minute = jodaDate.getMinuteOfHour();

			if (minute >= 30) {

				
				DateTime tempTime = jodaDate.plusHours(1);
				
				

				row[0] = Utility.converToDateHH(tempTime.toDate());

			} else {
				row[0] = Utility.converToDateHH(jodaDate.toDate());
			}

			if (jodaDate.getHourOfDay()== 0 && data.indexOf(row) + 1 < data.size()) {
				int index = data.indexOf(row) + 1;
				row[1] = data.get(index)[1];
				row[2] = data.get(index)[2];
			}

		}

		return data;
	}

	/**
	 * convert the date format to yyyy-MM-dd HH:00:00
	 */
	public List<Object[]> dateConvert(List<Object[]> data) throws ParseException {
		for (Object[] row : data) {
			Timestamp jdkdate = (Timestamp) row[0];
			DateTime jodaDate = new DateTime(jdkdate);
			int minute = jodaDate.getMinuteOfHour();
	
			if (minute >= 30) {
				DateTime tempTime = jodaDate.plusHours(1);
				row[0] = Utility.converToDateHH(tempTime.toDate());
			} else {

				row[0] = Utility.converToDateHH(jodaDate.toDate());
			}
		}

		return data;
	}

	/**
	 * Set up today data based on today early data, current hour data and forecast today data
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> combineTodayData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<Object[]> today = new ArrayList<Object[]>();

		today.addAll(dao.getTodaysEarlyData());
		today.addAll(dao.getCurrentHourData());
		today.addAll(dao.getForecastTodayData());
		
		return today;
	}

	/**
	 * Calculate Max_t
	 * @param today
	 * @return
	 * @throws ParseException
	 */
	public float getMaxTemperature(List<Object[]> today) throws ParseException {
		float max = Utility.getMax(today);
		return max;
	}

	/**
	 * Calculate Min_t
	 * @param today
	 * @return
	 * @throws ParseException
	 */
	public float getMinTemperature(List<Object[]> today) throws ParseException {
		float min = Utility.getMin(today);
		return min;
	}

	/**
	 * Combine today data, recent data or historical data and cooling load data
	 * @param today
	 * @param RecentData
	 * @param CLData
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> combineData(List<Object[]> today,List<Object[]> RecentData, List<PermanentHistoryData> CLData)
			throws ParseException {
		float max = getMaxTemperature(today);
		float min = getMinTemperature(today);

		List<Object[]> combinedata = new ArrayList<Object[]>();

		// Recent data
		for (Object[] element : RecentData) {
			Object[] array = new Object[8];
			array[0] = element[0];
			Date date = (Date) element[0];
			DateTime dt = new DateTime(date);
			// day of week
			if(dt.getDayOfWeek() == 7){
				array[1] = 0;
			}else{
				array[1] = dt.getDayOfWeek();
			}
			
			// hour of day
			array[2] = dt.getHourOfDay();
			// Max_t
			array[3] = Float.valueOf(element[1].toString());
			// Min_t
			array[4] = Float.valueOf(element[2].toString());
			// T_t
			array[5] = Float.valueOf(element[3].toString());
			// RH_t
			array[6] = Float.valueOf(element[4].toString());
			combinedata.add(array);
		}

		// today data
		for (Object[] element : today) {
			Object[] array = new Object[8];
			Date date = (Date) element[0];
			DateTime dt = new DateTime(date);
			DateTime now = new DateTime();

			if (dt.getHourOfDay() <= now.getHourOfDay()) {
				//Date
				array[0] = element[0];
				// day of week
				if(dt.getDayOfWeek() == 7){
					array[1] = 0;
				}else{
					array[1] = dt.getDayOfWeek();
				}
				// hour of day
				array[2] = dt.getHourOfDay();
				// Max_t
				array[3] = max;
				// Min_t
				array[4] = min;
				// T_t
				array[5] = element[1];
				// RH_t
				array[6] = element[2];
				combinedata.add(array);
			}
		}

		Map<Timestamp, Float> clMap = new HashMap<Timestamp, Float>();
		for (PermanentHistoryData element : CLData) {
			clMap.put(element.getId().getHistDateTime(), element.getHistValue());
		}

		for (Object[] element : combinedata) {
			element[7] = clMap.get(element[0]);
		}

		if(Utility.DEBUG){
			Utility.printArrayList(combinedata);
		}


		return combinedata;
	}
	
	/**
	 * combine next 24 hours data to original combine data
	 * @param next24Hours
	 * @param combineData
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> combineNext24HoursData(List<Object[]> next24Hours, List<Object[]> combineData) throws ParseException{
		float max = getMaxTemperature(next24Hours);
		float min = getMinTemperature(next24Hours);
		
		List<Object[]> combinedata = new ArrayList<Object[]>();
		
		// next 24 hours data
		for (Object[] element : next24Hours) {
				Object[] array = new Object[8];
				Date date = (Date) element[0];
				DateTime dt = new DateTime(date);
				
				//Date
				array[0] = element[0];
				// day of week
				if(dt.getDayOfWeek() == 7){
					array[1] = 0;
				}else{
					array[1] = dt.getDayOfWeek();
				}
				// hour of day
				array[2] = dt.getHourOfDay();
				// Max_t
				array[3] = max;
				// Min_t
				array[4] = min;
				// T_t
				array[5] = element[1];
				// RH_t
				array[6] = element[2];
				
				//initial forcast cooling load value to -1
				array[7] = -1f;
				combinedata.add(array);
			
		}
		
		//retrieve latest 168 elements
		int counter = 0;
		for(int i = combineData.size()-1; i >= 0; i--){
				combinedata.add(combineData.get(i));
				counter++;
				if(counter == 169){
					break;
				}
		}
		
		Collections.sort(combinedata, new SortByDate());
		
		return combinedata;
		
	}
	
	/**
	 * Insert the missing data from database to combine data.
	 * @param combine 
	 * @param CLData
	 * @param T_tandRH_t Missding data stored in HashMap based on Timestamp
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> insertMissDataToCombine(List<Object[]> combine, List<PermanentHistoryData> CLData,  List<Map<Timestamp, Float>> T_tandRH_t) throws ParseException{
		
		if(combine == null || CLData == null || T_tandRH_t == null){
			throw new NullPointerException();
		}
		Map<Timestamp, Float> T_t = T_tandRH_t.get(0);
		Map<Timestamp, Float> RH_t = T_tandRH_t.get(1);
 		
		Set<Timestamp> timeStampSet = T_t.keySet();
		//remove the duplicate data in T_tandRH_t
		for(Object[] el : combine){
			if(timeStampSet.contains(el[0])){
				T_t.remove(el[0]);
				RH_t.remove(el[0]);
			}
		}
		
		for(Timestamp el : T_t.keySet()){
			Object[] array = new Object[8];
			array[0] = el;
			
			DateTime dt = new DateTime(el);
			// day of week
			if(dt.getDayOfWeek() == 7){
				array[1] = 0;
			}else{
				array[1] = dt.getDayOfWeek();
			}
			// hour of day
			array[2] = dt.getHourOfDay();
			// Max_t
			array[3] = (float)0;
			// Min_t
			array[4] = (float)0;
			// T_t
			array[5] = T_t.get(el);
			// RH_t
			array[6] = RH_t.get(el);
			combine.add(array);
		}
		
		
		
		Map<Timestamp, Float> clMap = new HashMap<Timestamp, Float>();
		for (PermanentHistoryData element : CLData) {
			clMap.put(element.getId().getHistDateTime(), element.getHistValue());
		}

		for (Object[] element : combine) {
			element[7] = clMap.get(element[0]);
		}
		
		
		Collections.sort(combine, new SortByDate());
		
		
		return combine;
		
	}
	
	
	
	
	/**
	 * after error tolerant, reset the max_t and Min_t
	 * @param combine
	 */
	public void resetMaxMinTemperature(List<Object[]> combine){
		
		int day = 0;
		int lastday = 0;
		float max_t = Float.MIN_VALUE;
		float min_t = Float.MAX_VALUE;
		Map<String, Float> max_map = new HashMap<String, Float>();
		Map<String, Float> min_map = new HashMap<String, Float>();

		for(int i = 0; i < combine.size(); i++){
			 
			 day = (int) combine.get(i)[1];
			 if(day == lastday){
				 max_t = Math.max(max_t, (float) combine.get(i)[5]);
				 if((float) combine.get(i)[5] != 0){
					 min_t = Math.min(min_t, (float) combine.get(i)[5]);
				 }
				 DateTime temp = new DateTime(combine.get(i)[0]);
				 String date = temp.toString("yyyy-MM-dd");
				 max_map.put(date, max_t);
				 min_map.put(date, min_t);
			 }else{
				 max_t = Math.max(Float.MIN_VALUE, (float) combine.get(i)[5]);
				 min_t = Math.min(Float.MAX_VALUE, (float) combine.get(i)[5]);
				 DateTime temp = new DateTime(combine.get(i)[0]);
				 String date = temp.toString("yyyy-MM-dd");
				 max_map.put(date, max_t);
				 min_map.put(date, min_t);
				 
			 }
			 
			 lastday = day;
			 
		}
		
		for(int i = 0; i < combine.size(); i++){
			
			DateTime temp = new DateTime(combine.get(i)[0]);
			String date = temp.toString("yyyy-MM-dd");
			combine.get(i)[3] = max_map.get(date);
			combine.get(i)[4] = min_map.get(date);
		}
		
	}
	
	
	

//	public static void main(String[] args) throws ParseException, BiffException, IOException {
//		ARCIVE_DBDaoImpl dao = new ARCIVE_DBDaoImpl();
//		LocalDBDaoImpl ldao = new LocalDBDaoImpl();
//		List<Object[]> lastWeekData = dao.getLastWeekData();
//		List<Object[]> today =dao.getTodayData();
//		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();
//		List<Object[]> combine = dao.combineData(today, lastWeekData, clData);
//		 List<Map<Timestamp, Float>> T_tandRH_t = ldao.getLastWeekMissData();
//		 dao.insertMissData(combine, clData, T_tandRH_t);
//		 List<Object[]> next24hours = dao.getForcastNext24Hours();
//		 
//		 List<Object[]> combine24 = dao.combineNext24HoursData(next24hours, combine);
//		 dao.resetMaxMin_t(combine24);
//		 
//		 System.out.println(combine24.size());
//		 Utility.printArrayList(combine24);
//
//	}

}
