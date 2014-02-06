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
		CoolingLoadDBDaoImpl ldao = new CoolingLoadDBDaoImpl();
		List<Object[]> hData =dao.getHistData(); 
		List<Object[]> rData =dao.getRecentData();
		rData.addAll(hData); //retrieve 3 years data
		
		/*
		 * remove duplicate data
		 */
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
		
		//retrieve today's data
		List<Object[]> today =dao.combineTodayData();
		//retrieve Cooling load data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();
		//combine all data together
		List<Object[]> combine = dao.combineData(today, rData, clData);
		//retrieve missing data(error tolerant)
		List<Map<Timestamp, Float>> T_tandRH_t =ldao.getHistMissData();
		//insert missing data into 'combine'
		combine = dao.insertMissDataToCombine(combine, clData, T_tandRH_t);
		
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
		CoolingLoadDBDaoImpl ldao = new CoolingLoadDBDaoImpl();
		List<Object[]> lastWeekData = dao.getLastWeekData();//query last week data
		List<Object[]> today =dao.combineTodayData();//query today data
		List<PermanentHistoryData> clData = dao.getPermanentHistCLData();//query recorded cooling load data
		List<Object[]> combine = dao.combineData(today, lastWeekData, clData);//combine three data above
		List<Map<Timestamp, Float>> T_tandRH_t = ldao.getLastWeekMissData();// query the missing data for last week to prevent t-168 getting null pointer exception
		dao.insertMissDataToCombine(combine, clData, T_tandRH_t);// insert the missing data to original combine data
		
		List<Object[]> next24hours = dao.getForcastNext24Hours();
		List<Object[]> combine24 = dao.combineNext24HoursData(next24hours, combine);
		
		dao.resetMaxMinTemperature(combine24);	// Reset the Max_t and Min_t
	 
		forcastWeatherData.read(combine24);
	}
	
	public IData getForecastWeatherData(){
		return this.forcastWeatherData;
		
	}
	
	
}
