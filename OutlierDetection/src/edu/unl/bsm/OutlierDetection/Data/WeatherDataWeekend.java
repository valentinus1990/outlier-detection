package edu.unl.bsm.OutlierDetection.Data;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.read.biff.BiffException;
import edu.unl.bsm.OutlierDetection.Dao.ARCHIVE_DBDaoImpl;
import edu.unl.bsm.OutlierDetection.Dao.CoolingLoadDBDaoImpl;
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
	
	
	/**
	 * @param histWeatherData the histNoaaData to set
	 * @throws ParseException 
	 */
	public void readHistWeatherData() throws ParseException {	
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl ldao = new CoolingLoadDBDaoImpl();
		List<Object[]> hData =dao.getHistData();
		List<Object[]> rData =dao.getRecentData();
		rData.addAll(hData);
		
		List<Object[]> filters = new ArrayList<Object[]>();
		Set<Timestamp> datefilter = new HashSet<Timestamp>();
		for(Object[] el: rData){
			if(!datefilter.contains(el[0])){
				datefilter.add((Timestamp) el[0]);
			}else{
				filters.add(el);
			}
		}
		rData.removeAll(filters);
		List<Object[]> today =dao.combineTodayData();
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();
		List<Object[]> combine = dao.combineData(today, rData, clData);
		List<Map<Timestamp, Float>> T_tandRH_t =ldao.getHistMissData();
		combine = dao.insertMissDataToCombine(combine, clData, T_tandRH_t);
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
	
	public void readForecastWeatherData() throws ParseException {
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();
		CoolingLoadDBDaoImpl ldao = new CoolingLoadDBDaoImpl();
		List<Object[]> lastWeekData = dao.getLastWeekData();//query last week data
		List<Object[]> today =dao.combineTodayData();//query today data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();//query reported cooling load data
		List<Object[]> combine = dao.combineData(today, lastWeekData, clData);//combine three data above
		List<Map<Timestamp, Float>> T_tandRH_t = ldao.getLastWeekMissData();// query the missing data for last week to prevent t-168 getting null pointer exception
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);// insert the missing data to original combine data
		
		List<Object[]> next24hours = dao.getForcastNext24Hours();
		List<Object[]> combine24 = dao.combineNext24HoursData(next24hours, combine);
		
		dao.resetMaxMinTemperature(combine24);	// Reset the Max_t and Min_t
	 
		forecastWeatherData.read(combine24);
	}
	
	public IData getForecastWeatherData(){
		return this.forecastWeatherData;
		
	}
	
	
}
