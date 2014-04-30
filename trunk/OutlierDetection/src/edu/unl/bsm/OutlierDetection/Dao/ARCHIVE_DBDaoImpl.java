package edu.unl.bsm.OutlierDetection.Dao;

import java.io.IOException;
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

import jxl.read.biff.BiffException;

import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Entity.OBJECTS;
import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;
import edu.unl.bsm.OutlierDetection.Util.Utility;

/**
 * @author ZhongYin Zhang
 * 
 */
public class ARCHIVE_DBDaoImpl implements IARCHIVE_DBDao {

	class SortByDate implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			if(o1 instanceof WeatherDataForm){
				return (((WeatherDataForm) o1).getDateCollected()).compareTo(((WeatherDataForm) o2).getDateCollected());
			}else{
				return (((WeatherDataFinalForm) o1).getDate()).compareTo(((WeatherDataFinalForm) o2).getDate());
			}
		}
	}
	
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
	 * Standard weather data from database
	 * The format is:
	 * Date Max_t Min_t T_t RH_t
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> getWeatherFormData() throws ParseException{
		Query query = em.createNativeQuery("SELECT HW.DATE_COLLECTED, " 
							+ " HW.DRY_BULB_F AS T_t, "
							+ " HW.RELATIVE_HUMIDITY_PCT AS RH_t FROM" 
							+ " HOURLY_WEATHER AS HW " 
							+ " WHERE " 
							+ " HW.DATE_COLLECTED > CURRENT_TIMESTAMP - INTERVAL 3 YEAR AND"
							+ " HW.DATE_COLLECTED < CURRENT_DATE() - INTERVAL 10 MINUTE" 
							+ " ORDER BY HW.DATE_COLLECTED ");
		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list);
		dataProcess(result);
		List<WeatherDataForm> newResult = filterDuplicateData(result);
		Collections.sort(newResult, new SortByDate());
		return newResult;
	} 
	/**
	 * Filter duplicate data 
	 * @param weatherData
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> filterDuplicateData(List<WeatherDataForm> weatherData) throws ParseException{
		if(weatherData == null){
			throw new IllegalArgumentException();
		}
		Set<Date> filter = new HashSet<Date>();
		dateConvert(weatherData);
		List<WeatherDataForm> newResult = new ArrayList<WeatherDataForm>();
		for (WeatherDataForm element : weatherData) {
			if (!filter.contains(element.getDateCollected())) {
				filter.add(element.getDateCollected());
				newResult.add(element);
			}else{
//				System.out.println("Duplicated Date: "+ element[0]);
			}
		}
		return newResult;
	}
	
	/**
	 * Filter duplicate data for today and forecast
	 * @param weatherData
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> filterDuplicateDataToday(List<WeatherDataForm> weatherData) throws ParseException{
		if(weatherData == null){
			throw new IllegalArgumentException();
		}
		Set<Date> filter = new HashSet<Date>();
		dateConvertForToday(weatherData);
		List<WeatherDataForm> newResult = new ArrayList<WeatherDataForm>();
		for (WeatherDataForm element : weatherData) {
			if (!filter.contains(element.getDateCollected())) {
				filter.add(element.getDateCollected());
				newResult.add(element);
			}else{
//				System.out.println("Duplicated Date: "+ element[0]);
			}
		}
		return newResult;
	}
	
	
	/**
	 * Retrieve Data one week ago
	 * 
	 * @throws ParseException
	 */
	
	public List<WeatherDataForm> getLastWeekData() throws ParseException {
		Query query = em.createNativeQuery("SELECT HW.DATE_COLLECTED, "
				+ "HW.DRY_BULB_F AS T_t, "
				+ "HW.RELATIVE_HUMIDITY_PCT AS RH_t " + "FROM "
				+ "HOURLY_WEATHER AS HW "
				+ "WHERE "
				+ "HW.DATE_COLLECTED > CURRENT_TIMESTAMP - INTERVAL 2 WEEK AND "
				+ "HW.DATE_COLLECTED < CURRENT_DATE() - INTERVAL 10 MINUTE "
				+ "ORDER BY HW.DATE_COLLECTED ");

		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list);
		dataProcess(result);
		List<WeatherDataForm> newResult = filterDuplicateData(result);
		Collections.sort(newResult, new SortByDate());
		return newResult;
	}
	

	/**
	 * Get the prediction data after current hour until 23:00 today
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> getForecastTodayData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE Hour(FORECAST_TARGET) > Hour(NOW()) AND "
						+ "							date(FORECAST_TARGET) = CURRENT_DATE AND date(FORECAST_OBTAINED) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC");
		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list); 
		List<WeatherDataForm> newResult = filterDuplicateDataToday(result);
		Collections.sort(newResult, new SortByDate());
//		Utility.printArrayList(newResult);
		return newResult;
	}

	
	
	/**
	 * get next 24 hours temperature and Relative humidity
	 * @return
	 * @throws ParseException
	 */
	
	public List<WeatherDataForm> getForcastNext24Hours() throws ParseException{
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE FORECAST_TARGET> NOW() AND FORECAST_TARGET < NOW() + INTERVAL 49 HOUR AND " +
						"							date(FORECAST_OBTAINED) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC");
		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list); 
		List<WeatherDataForm> newResult = filterDuplicateDataToday(result);
		Collections.sort(newResult, new SortByDate());
//		Utility.printArrayList(newResult);
		return newResult;
		
	}

	/**
	 * Get todays data between 0 to last hour
	 * 
	 * @return
	 * @throws ParseException
	 */

	public List<WeatherDataForm> getTodaysEarlyData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT DATE_COLLECTED, DRY_BULB_F AS T_t, RELATIVE_HUMIDITY_PCT AS RH_t FROM HOURLY_WEATHER "
						+ "							WHERE DATE_COLLECTED > CURRENT_DATE()-  INTERVAL 10 MINUTE ");

		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list); 
		List<WeatherDataForm> newResult = filterDuplicateDataToday(result);
		Collections.sort(newResult, new SortByDate());
		return newResult;
	}

	/**
	 * get the data information just for current hour
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> getCurrentHourData() throws ParseException {
		Query query = em
				.createNativeQuery("SELECT FORECAST_TARGET, TEMPERATURE_F AS T_t, REL_HUMIDITY_PCT AS RH_t FROM PAST_FORECASTS "
						+ "							WHERE Hour(FORECAST_TARGET) = Hour(NOW()) AND "
						+ "							date(FORECAST_OBTAINED) = CURRENT_DATE AND DATE(FORECAST_TARGET) = CURRENT_DATE "
						+ "							ORDER BY FORECAST_OBTAINED DESC LIMIT 0 , 1");

		List<Object[]> list = query.getResultList();
		List<WeatherDataForm> result = convertObjToWeatherDataForm(list); 
		List<WeatherDataForm> newResult = filterDuplicateDataToday(result);
		Collections.sort(newResult, new SortByDate());
		return newResult;
	}

	/**
	 * Process the data retrieved from database to a regular data format
	 * eg: 13:31:24 --> 14:00:00
	 * 	   13:29:11 --> 13:00:00
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public void dataProcess(List<WeatherDataForm> data) throws ParseException {
		if(data == null){
			throw new IllegalArgumentException();
		}
		for (WeatherDataForm row : data) {
			Date jdkdate = row.getDateCollected();
			DateTime jodaDate = new DateTime(jdkdate);
			int minute = jodaDate.getMinuteOfHour();
			if (minute >= 30) {
				DateTime tempTime = jodaDate.plusHours(1);
				row.setDateCollected(Utility.converToDateHH(tempTime.toDate()));
			} else {
				row.setDateCollected(Utility.converToDateHH(jodaDate.toDate()));
			}
//			if (jodaDate.getHourOfDay()== 0 && data.indexOf(row) + 1 < data.size()) {
//				int index = data.indexOf(row) + 1;
//				row.setMaxTemp(data.get(index).getMaxTemp());
//				row.setMinTemp(data.get(index).getMinTemp());
//			}
		}
	}

	/**
	 * convert the date format to yyyy-MM-dd HH:00:00
	 */
	public void dateConvert(List<WeatherDataForm> data) throws ParseException {
		if(data == null){
			throw new IllegalArgumentException();
		}
		for (WeatherDataForm row : data) {
			Date jdkdate = row.getDateCollected();
			DateTime jodaDate = new DateTime(jdkdate);
			int minute = jodaDate.getMinuteOfHour();
	
			if (minute >= 30) {
				DateTime tempTime = jodaDate.plusHours(1);
				row.setDateCollected(Utility.converToDateHH(tempTime.toDate()));
			} else {
				row.setDateCollected(Utility.converToDateHH(jodaDate.toDate()));
			}
		}
	}
	
	/**
	 * convert the date format to yyyy-mm-dd HH:00:00 for today's data
	 * @param data
	 * @throws ParseException
	 */
	public void dateConvertForToday(List<WeatherDataForm> data) throws ParseException{
		if(data == null){
			throw new IllegalArgumentException();
		}
		for (WeatherDataForm row : data) {
			Date jdkdate = row.getDateCollected();
			DateTime jodaDate = new DateTime(jdkdate);
			int minute = jodaDate.getMinuteOfHour();
	
			if (minute >= 30) {
				DateTime tempTime = jodaDate.plusHours(1);
				row.setDateCollected(Utility.converToDateHH(tempTime.toDate()));
			} else {
				row.setDateCollected(Utility.converToDateHH(jodaDate.toDate()));
			}
		}
	}
	

	/**
	 * Set up today data based on today early data, current hour data and forecast today data
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataForm> combineTodayData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		List<WeatherDataForm> today = new ArrayList<WeatherDataForm>();
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
	public float getMaxTemperature(List<WeatherDataForm> today) throws ParseException {
		float max = Utility.getMax(today);
		return max;
	}

	/**
	 * Calculate Min_t
	 * @param today
	 * @return
	 * @throws ParseException
	 */
	public float getMinTemperature(List<WeatherDataForm> today) throws ParseException {
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
	public List<WeatherDataFinalForm> combineData(List<WeatherDataForm> today,List<WeatherDataForm> histWeatherData, List<PermanentHistoryData> CLData)
			throws ParseException {
		float max = getMaxTemperature(today);
		float min = getMinTemperature(today);

		List<WeatherDataFinalForm> combinedata = new ArrayList<WeatherDataFinalForm>();
		// Weather Data Form
		for (WeatherDataForm element : histWeatherData) {
			WeatherDataFinalForm wdff = new WeatherDataFinalForm(); 
			Timestamp date = element.getDateCollected();
			wdff.setDate(date);
			DateTime dt = new DateTime(date);
			// day of week
			if(dt.getDayOfWeek() == 7){
				 wdff.setDayOfWeek(0);
			}else{
				 wdff.setDayOfWeek(dt.getDayOfWeek());
			}
			// hour of day
			wdff.setHourOfDay(dt.getHourOfDay());
			// Max_t
			wdff.setMaxTemp(Integer.MIN_VALUE);
			// Min_t
			wdff.setMinTemp(Integer.MAX_VALUE);
			// T_t
			wdff.setCurTemp(element.getCurrentTemp());
			// RH_t
			wdff.setRelativeHum(element.getRelativeHum());
			combinedata.add(wdff);
		}

		// today data
		for (WeatherDataForm element : today) {
			WeatherDataFinalForm wdff = new WeatherDataFinalForm();
			Timestamp date = element.getDateCollected();
			DateTime dt = new DateTime(date);
			DateTime now = new DateTime();

			if (dt.getHourOfDay() <= now.getHourOfDay()) {
				//Date
				wdff.setDate(date);
				// day of week
				if(dt.getDayOfWeek() == 7){
					wdff.setDayOfWeek(0);
				}else{
					wdff.setDayOfWeek(dt.getDayOfWeek());
				}
				// hour of day
				wdff.setHourOfDay(dt.getHourOfDay());
				// Max_t
				wdff.setMaxTemp(max);
				// Min_t
				wdff.setMinTemp(min);
				// T_t
				wdff.setCurTemp(element.getCurrentTemp());
				// RH_t
				wdff.setRelativeHum(element.getRelativeHum());
				combinedata.add(wdff);
			}
		}

		Map<Timestamp, Float> clMap = new HashMap<Timestamp, Float>();
		for (PermanentHistoryData element : CLData) {
			clMap.put(element.getId().getHistDateTime(), element.getHistValue());
		}

		for (WeatherDataFinalForm element : combinedata) {
			if(clMap.get(element.getDate()) != null)
				element.setCoolingLoad(clMap.get(element.getDate()));
			
		}

//		if(Utility.DEBUG){
//			Utility.printArrayList(combinedata);
//		}
		return combinedata;
	}
	
	/**
	 * combine next 24 hours data to original combine data
	 * @param next24Hours
	 * @param combine
	 * @return
	 * @throws ParseException
	 */
	public List<WeatherDataFinalForm> combineNext24HoursData(List<WeatherDataForm> next24Hours, List<WeatherDataFinalForm> combine) throws ParseException{
		float max = getMaxTemperature(next24Hours);
		float min = getMinTemperature(next24Hours);
		
		List<WeatherDataFinalForm> combinedata = new ArrayList<WeatherDataFinalForm>();
		
		// next 24 hours data
		for (WeatherDataForm element : next24Hours) {
			WeatherDataFinalForm wdff = new WeatherDataFinalForm();
				Timestamp date = element.getDateCollected();
				DateTime dt = new DateTime(date);
				//Date
				wdff.setDate(date);
				// day of week
				if(dt.getDayOfWeek() == 7){
					wdff.setDayOfWeek(0);
				}else{
					wdff.setDayOfWeek(dt.getDayOfWeek());
				}
				// hour of day
				wdff.setHourOfDay(dt.getHourOfDay());
				// Max_t
				wdff.setMaxTemp(max);
				// Min_t
				wdff.setMinTemp(min);
				// T_t
				wdff.setCurTemp(element.getCurrentTemp());
				// RH_t
				wdff.setRelativeHum(element.getRelativeHum());
				//initial forcast cooling load value to -1
				wdff.setCoolingLoad(-1);
				combinedata.add(wdff);
			
		}
		//retrieve latest 168 elements
		int counter = 0;
		for(int i = combine.size()-1; i >= 0; i--){
				combinedata.add(combine.get(i));
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
	public void insertMissDataToCombine(List<WeatherDataFinalForm> combine, List<PermanentHistoryData> CLData,  List<Map<Timestamp, Float>> T_tandRH_t) throws ParseException{
		
		if(combine == null || CLData == null || T_tandRH_t == null){
			throw new IllegalArgumentException();
		}
		Map<Timestamp, Float> T_t = T_tandRH_t.get(0);
		Map<Timestamp, Float> RH_t = T_tandRH_t.get(1);
 		
		Set<Timestamp> timeStampSet = T_t.keySet();
		//remove the duplicate data in T_tandRH_t
		for(WeatherDataFinalForm el : combine){
			if(timeStampSet.contains(el.getDate())){
				T_t.remove(el.getDate());
				RH_t.remove(el.getDate());
			}
		}
		for(Timestamp el : T_t.keySet()){
			WeatherDataFinalForm wdff = new WeatherDataFinalForm();
			wdff.setDate(el);
			
			DateTime dt = new DateTime(el);
			// day of week
			if(dt.getDayOfWeek() == 7){
				wdff.setDayOfWeek(0);
			}else{
				wdff.setDayOfWeek(dt.getDayOfWeek());
			}
			// hour of day
			wdff.setHourOfDay(dt.getHourOfDay());
			// Max_t
			wdff.setMaxTemp(0);
			// Min_t
			wdff.setMinTemp(0);
			// T_t
			wdff.setCurTemp(T_t.get(el));
			// RH_t
			wdff.setRelativeHum(RH_t.get(el));
			combine.add(wdff);
		}
		Map<Timestamp, Float> clMap = new HashMap<Timestamp, Float>();
		for (PermanentHistoryData element : CLData) {
			clMap.put(element.getId().getHistDateTime(), element.getHistValue());
		}

		for (WeatherDataFinalForm element : combine) {
			if(clMap.get(element.getDate()) != null)
				element.setCoolingLoad(clMap.get(element.getDate()));
		}
		Collections.sort(combine, new SortByDate());
	}
	
	/**
	 * after error tolerant, reset the max_t and Min_t
	 * @param combine
	 */
	public void resetMaxMinTemperature(List<WeatherDataFinalForm> combine){
		if(combine == null){
			throw new IllegalArgumentException();
		}
		int day = 0;
		int lastday = 0;
		float max_t = Float.MIN_VALUE;
		float min_t = Float.MAX_VALUE;
		Map<String, Float> max_map = new HashMap<String, Float>();
		Map<String, Float> min_map = new HashMap<String, Float>();

		for(int i = 0; i < combine.size(); i++){
			 day = (int) combine.get(i).getDayOfWeek();
			 if(day == lastday){
				 max_t = Math.max(max_t, (float) combine.get(i).getCurTemp());
				 if((float) combine.get(i).getCurTemp() != 0){
					 min_t = Math.min(min_t, (float) combine.get(i).getCurTemp());
				 }
				 DateTime temp = new DateTime(combine.get(i).getDate());
				 String date = temp.toString("yyyy-MM-dd");
				 max_map.put(date, max_t);
				 min_map.put(date, min_t);
			 }else{
				 max_t = Math.max(Float.MIN_VALUE, (float) combine.get(i).getCurTemp());
				 min_t = Math.min(Float.MAX_VALUE, (float) combine.get(i).getCurTemp());
				 DateTime temp = new DateTime(combine.get(i).getDate());
				 String date = temp.toString("yyyy-MM-dd");
				 max_map.put(date, max_t);
				 min_map.put(date, min_t);
			 }
			 lastday = day;
		}
		
		for(int i = 0; i < combine.size(); i++){
			DateTime temp = new DateTime(combine.get(i).getDate());
			String date = temp.toString("yyyy-MM-dd");
			combine.get(i).setMaxTemp(max_map.get(date));
			combine.get(i).setMinTemp(min_map.get(date));
		}
	}
	
	/**
	 * Convert Object[] to wrap class
	 * @param list
	 * @return
	 */
	public List<WeatherDataForm> convertObjToWeatherDataForm(List<Object[]> list){
		if(list == null){
			throw new IllegalArgumentException();
		}
		List<WeatherDataForm> result = new ArrayList<WeatherDataForm>();
		for(Object[] array : list){
			Timestamp date = (Timestamp) array[0];
			float cur_t = (float) array[1];
			float RH_t = (float) array[2];
			WeatherDataForm wdf = new WeatherDataForm(date, cur_t, RH_t);
			result.add(wdf);
		}
		return result;
	}
	

	

	public static void main(String[] args) throws ParseException, BiffException, IOException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		dao.getWeatherFormData();
		
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

	}

}
