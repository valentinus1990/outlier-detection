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


/**
 * 
 * @author ZhongYin Zhang
 *
 */
public class WeatherDataWeekend implements IWeatherDataWeek{
	/**Historical Weather Data*/
	private IData histWeatherData;
	/**Forecast Data*/
	private IData forecastWeatherData;
	
	
	public WeatherDataWeekend() throws BiffException, IOException {
		this.histWeatherData = new WeatherData();
		this.forecastWeatherData = new WeatherData();
	}
	
	
	/**Read history weather data from database
	 * @throws ParseException 
	 */
	public void readHistWeatherData() throws ParseException {	
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl ldao = CoolingLoadDBDaoImpl.getInstance();
		List<WeatherDataForm> weatherData =dao.getWeatherFormData(); 
		
		List<WeatherDataForm> today =dao.combineTodayData();
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();
		List<WeatherDataFinalForm> combine = dao.combineData(today, weatherData, clData);
		List<Map<Timestamp, Float>> T_tandRH_t =ldao.getHistMissData();
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);
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
	 * Read history weather data from database
	 */
	public void readForecastWeatherData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl ldao = CoolingLoadDBDaoImpl.getInstance();
		List<WeatherDataForm> lastWeekData = dao.getLastWeekData();//query last week data
		List<WeatherDataForm> today =dao.combineTodayData();//query today data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();//query reported cooling load data
		List<WeatherDataFinalForm> combine = dao.combineData(today, lastWeekData, clData);//combine three data above
		List<Map<Timestamp, Float>> T_tandRH_t = ldao.getLastWeekMissData();// query the missing data for last week to prevent t-168 getting null pointer exception
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);// insert the missing data to original combine data
		
		List<WeatherDataForm> next24hours = dao.getForcastNext24Hours();
		List<WeatherDataFinalForm> combine24 = dao.combineNext24HoursData(next24hours, combine);
		
		dao.resetMaxMinTemperature(combine24);	// Reset the Max_t and Min_t
	 
		forecastWeatherData.read(combine24);
	}
	
	public IData getForecastWeatherData(){
		return this.forecastWeatherData;
		
	}
	
	
}
