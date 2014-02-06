package edu.unl.bsm.OutlierDetection.Data;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.read.biff.BiffException;




public class WeatherData implements IData {
	
	private String inputFile;
	
	//Max Temperature
	private HashMap<Date, Float> T_MAX_t;
	
	//the length of the column
	private  int dataSize; 
	
	//Min Temperature
	private HashMap<Date, Float> T_MIN_t;
	
	//Temperature at that hour point
	private HashMap<Date, Float> T_t; 
	
	//Relative humidity
	private HashMap<Date, Float> RH_t; 
	
	//Cooling load at that hour point
	private HashMap<Date, Float> coolLoad_t; 
	
	//local time
	private HashMap<Date, String> localTime;
	
	//day of week
	
	private HashMap<Date, Integer> dayofWeek;
	
	//hour of the day
	private HashMap<Date, Integer> hour;
	
	
	private float Tmaxt;
	
	private float Tmint;
	
	private float Tt;
	
	private float RHt;
	
	private float coolLoadt;
	

	
	/**
	 * Constructor
	 * @throws IOException
	 * @throws BiffException
	 */
	public  WeatherData() throws IOException, BiffException {
			
			this.inputFile = null;
			this.T_MAX_t = new HashMap<Date, Float>();
			this.dataSize = 0;
			this.T_MIN_t = new HashMap<Date, Float>();
			this.T_t = new HashMap<Date, Float>();
			this.RH_t = new HashMap<Date, Float>();
			this.coolLoad_t = new HashMap<Date, Float>();
			this.localTime = new HashMap<Date, String>();
			this.dayofWeek = new HashMap<Date, Integer>();
			this.hour = new HashMap<Date, Integer>();
			
		
		}
	
	/**
	 * Read data and put them to their corresponding HashMap
	 */
	@Override
	public void read(List<Object[]> weatherData){
		for(Object[] wdata : weatherData){
			
			Date date = (Date)wdata[0];
			localTime.put(date, date.toString());
			
			dayofWeek.put(date, (int) wdata[1]);
			hour.put(date, (int) wdata[2]);
			T_MAX_t.put(date, (float)wdata[3]);
			T_MIN_t.put(date, (float)wdata[4]);
			T_t.put(date, (float)wdata[5]);
			RH_t.put(date, (float)wdata[6]);
			if(wdata[7] == null){
				coolLoad_t.put(date, (float)1);
			}else{
				coolLoad_t.put(date, (float)wdata[7]);
			}
			
		}
		
		dataSize = localTime.size();
		
//		System.out.println(localTime.size()+ " " + coolLoad_t.size());
	}

	
	/**
	 * @return the t_MAX_t
	 */
	public HashMap<Date, Float> getT_MAX_t() {
		return T_MAX_t;
	}

	/**
	 * @return the dataSize
	 */
	public int getDataSize() {
		return dataSize;
	}

	/**
	 * @return the t_MIN_t
	 */
	public HashMap<Date, Float> getT_MIN_t() {
		return T_MIN_t;
	}

	/**
	 * @return the t_t
	 */
	public HashMap<Date, Float> getT_t() {
		return T_t;
	}

	/**
	 * @return the rH_t
	 */
	public HashMap<Date, Float> getRH_t() {
		return RH_t;
	}

	/**
	 * @return the coolLoad_t
	 */
	public HashMap<Date, Float> getCoolLoad_t() {
		return coolLoad_t;
	}

	/**
	 * @return the localTime
	 */
	public HashMap<Date, String> getLocalTime() {
		return localTime;
	}

	/**
	 * @return the dayofWeek
	 */
	public HashMap<Date, Integer> getDayofWeek() {
		return dayofWeek;
	}

	/**
	 * @return the hour of the day
	 */
	public HashMap<Date, Integer> getHour() {
		return hour;
	}
	
	/**
	 * @param t_MAX_t the t_MAX_t to set
	 */
	public void setT_MAX_t(HashMap<Date, Float> t_MAX_t) {
		T_MAX_t = t_MAX_t;
	}

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	/**
	 * @param t_MIN_t the t_MIN_t to set
	 */
	public void setT_MIN_t(HashMap<Date, Float> t_MIN_t) {
		T_MIN_t = t_MIN_t;
	}

	/**
	 * @param t_t the t_t to set
	 */
	public void setT_t(HashMap<Date, Float> t_t) {
		T_t = t_t;
	}

	/**
	 * @param rH_t the rH_t to set
	 */
	public void setRH_t(HashMap<Date, Float> rH_t) {
		RH_t = rH_t;
	}

	/**
	 * @param coolLoad_t the coolLoad_t to set
	 */
	public void setCoolLoad_t(HashMap<Date, Float> coolLoad_t) {
		this.coolLoad_t = coolLoad_t;
	}

	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(HashMap<Date, String> localTime) {
		this.localTime = localTime;
	}

	/**
	 * @param dayofWeek the dayofWeek to set
	 */
	public void setDayofWeek(HashMap<Date, Integer> dayofWeek) {
		this.dayofWeek = dayofWeek;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(HashMap<Date, Integer> hour) {
		this.hour = hour;
	}



	/**
	 * @return the tmaxt
	 */
	public float getTmaxt() {
		return Tmaxt;
	}

	/**
	 * @param tmaxt the tmaxt to set
	 */
	public void setTmaxt(float tmaxt) {
		Tmaxt = tmaxt;
	}

	/**
	 * @return the tmint
	 */
	public float getTmint() {
		return Tmint;
	}

	/**
	 * @param tmint the tmint to set
	 */
	public void setTmint(float tmint) {
		Tmint = tmint;
	}

	/**
	 * @return the tt
	 */
	public float getTt() {
		return Tt;
	}

	/**
	 * @param tt the tt to set
	 */
	public void setTt(float tt) {
		Tt = tt;
	}

	/**
	 * @return the rHt
	 */
	public float getRHt() {
		return RHt;
	}

	/**
	 * @param rHt the rHt to set
	 */
	public void setRHt(float rHt) {
		RHt = rHt;
	}

	/**
	 * @return the coolLoadt
	 */
	public float getCoolLoadt() {
		return coolLoadt;
	}

	/**
	 * @param coolLoadt the coolLoadt to set
	 */
	public void setCoolLoadt(float coolLoadt) {
		this.coolLoadt = coolLoadt;
	}

	/**
	 * @return the inputFile
	 */
	public String getInputFile() {
		return inputFile;
	}


	/**
	 * This read method is used for testing
	 */
	@Override
	public void read() throws IOException, BiffException {
	}
	
	@Override
	public void setInputFile(String inputFile) {
    
	}

	
	
	

}
