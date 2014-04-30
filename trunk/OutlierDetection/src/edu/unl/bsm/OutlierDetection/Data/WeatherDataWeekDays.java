package edu.unl.bsm.OutlierDetection.Data;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import edu.unl.bsm.OutlierDetection.Dao.ARCHIVE_DBDaoImpl;
import edu.unl.bsm.OutlierDetection.Dao.CoolingLoadDBDaoImpl;
import edu.unl.bsm.OutlierDetection.Dao.WeatherDataFinalForm;
import edu.unl.bsm.OutlierDetection.Dao.WeatherDataForm;
import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;


public class WeatherDataWeekDays implements IWeatherDataWeek{
	
	private IData histWeatherData;	// HashMaps for Historical data
	private IData forcastWeatherData; //HashMaps for future data
	


	public WeatherDataWeekDays() throws BiffException, IOException {
		this.histWeatherData = new WeatherData();
		this.forcastWeatherData = new WeatherData();
	}
	
	
	/**
	 * Retrieve Historical data from database
	 * @param histWeatherData the histNoaaData to set
	 * @throws ParseException 
	 */
	public void readHistWeatherData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl cldao = CoolingLoadDBDaoImpl.getInstance();
		List<WeatherDataForm> weatherData =dao.getWeatherFormData(); 
//		List<Object[]> rData =dao.getRecentData();
//		rData.addAll(weatherData); //retrieve 3 years data
		
		/*
		 * remove duplicate data
		 */
//		List<WeatherDataForm> filters = new ArrayList<WeatherDataForm>();
//		Set<Date> datefilter = new HashSet<Date>();
//		for(WeatherDataForm el: weatherData){
//			if(!datefilter.contains(el.getDateCollected())){
//				datefilter.add(el.getDateCollected());
//			}else{
//				filters.add(el);
//			}
//		}
//		rData.removeAll(filters);
		
		//retrieve today's data
		List<WeatherDataForm> today =dao.combineTodayData();
		//retrieve Cooling load data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();
		//combine all data together
		List<WeatherDataFinalForm> combine = dao.combineData(today, weatherData, clData);
		//retrieve missing data(error tolerant)
		List<Map<Timestamp, Float>> T_tandRH_t =cldao.getHistMissData();
		//insert missing data into 'combine'
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);
		//reset mat_t, Min_t
		dao.resetMaxMinTemperature(combine);
		
		histWeatherData.read(combine);
	}
	/**
	 * 
	 * @return return the HistnooaData from Database 
	 * @throws ParseException 
	 */
	public IData getHistWeatherData(){
		return this.histWeatherData;
		
	}
	/**
	 * get next 24 hours data from database
	 * @throws ParseException
	 */
	public void readForecastWeatherData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl ldao = CoolingLoadDBDaoImpl.getInstance();
		List<WeatherDataForm> lastWeekData = dao.getLastWeekData();//query last week data
		List<WeatherDataForm> today =dao.combineTodayData();//query today data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();//query recorded cooling load data
		List<WeatherDataFinalForm> combine = dao.combineData(today, lastWeekData, clData);//combine three data above
		List<Map<Timestamp, Float>> T_tandRH_t = ldao.getLastWeekMissData();// query the missing data for last week to prevent t-168 getting null pointer exception
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);// insert the missing data to original combine data
		
		List<WeatherDataForm> next24hours = dao.getForcastNext24Hours();
		List<WeatherDataFinalForm> combine24 = dao.combineNext24HoursData(next24hours, combine);
		
		dao.resetMaxMinTemperature(combine24);	// Reset the Max_t and Min_t
	 
		forcastWeatherData.read(combine24);
	}
	
	public IData getForecastWeatherData(){
		return this.forcastWeatherData;
		
	}
	
	
}
