package edu.unl.bsm.OutlierDetection.Data;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import jxl.read.biff.BiffException;


/**
 * 
 * @author ZhongYin Zhang
 *
 */
public interface IWeatherDataWeek {
	
	 public void readHistWeatherData() throws ParseException, IOException;
	 
	 public IData getHistWeatherData();
	 
	 public void readForecastWeatherData() throws ParseException, IOException;
	 
	 public IData getForecastWeatherData();
	
	

}
