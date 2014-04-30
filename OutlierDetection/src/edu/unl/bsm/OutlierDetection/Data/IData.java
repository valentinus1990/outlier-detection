package edu.unl.bsm.OutlierDetection.Data;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.read.biff.BiffException;
import edu.unl.bsm.OutlierDetection.Dao.WeatherDataFinalForm;


/**
 * 
 * 
 * @author ZhongYin Zhang
 *
 */
public interface IData {
	
	
	/**
	 * Read the data from Excel 
	 * @throws IOException
	 * @throws BiffException
	 */
	public void read() throws IOException, BiffException;
	
	/**
	 * 
	 * @param inputFile the file to be read.
	 */
	
	public void setInputFile(String inputFile);
	
	/**
	 * @return the t_MAX_t
	 */
	public HashMap<Date, Float> getT_MAX_t();

	/**
	 * @return the dataSize
	 */
	public int getDataSize();

	/**
	 * @return the t_MIN_t
	 */
	public HashMap<Date, Float> getT_MIN_t();

	/**
	 * @return the t_t
	 */
	public HashMap<Date, Float> getT_t();

	/**
	 * @return the rH_t
	 */
	public HashMap<Date, Float> getRH_t();

	/**
	 * @return the coolLoad_t
	 */
	public HashMap<Date, Float> getCoolLoad_t();

	/**
	 * @return the localTime
	 */
	public HashMap<Date, String> getLocalTime();
	
	/**
	 * @return the day of week
	 */
	public HashMap<Date, Integer> getDayofWeek();
	
	
	/**
	 * @return the day of week
	 */
	public HashMap<Date, Integer> getHour();
	
	
	/**
	 * @param t_MAX_t the t_MAX_t to set
	 */
	public void setT_MAX_t(HashMap<Date, Float> t_MAX_t);

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(int dataSize);

	/**
	 * @param t_MIN_t the t_MIN_t to set
	 */
	public void setT_MIN_t(HashMap<Date, Float> t_MIN_t);

	/**
	 * @param t_t the t_t to set
	 */
	public void setT_t(HashMap<Date, Float> t_t);

	/**
	 * @param rH_t the rH_t to set
	 */
	public void setRH_t(HashMap<Date, Float> rH_t);

	/**
	 * @param coolLoad_t the coolLoad_t to set
	 */
	public void setCoolLoad_t(HashMap<Date, Float> coolLoad_t);

	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(HashMap<Date, String> localTime);

	/**
	 * @param dayofWeek the dayofWeek to set
	 */
	public void setDayofWeek(HashMap<Date, Integer> dayofWeek);

	/**
	 * @param hour the hour to set
	 */
	public void setHour(HashMap<Date, Integer> hour);
	
	
//	public Cell[] getLocalTime_array();
	
	
	
	/**
	 * @return the tmaxt
	 */
	public float getTmaxt();

	/**
	 * @param tmaxt the tmaxt to set
	 */
	public void setTmaxt(float tmaxt);

	/**
	 * @return the tmint
	 */
	public float getTmint();

	/**
	 * @param tmint the tmint to set
	 */
	public void setTmint(float tmint);

	/**
	 * @return the tt
	 */
	public float getTt();

	/**
	 * @param tt the tt to set
	 */
	public void setTt(float tt);

	/**
	 * @return the rHt
	 */
	public float getRHt();

	/**
	 * @param rHt the rHt to set
	 */
	public void setRHt(float rHt);

	/**
	 * @return the coolLoadt
	 */
	public float getCoolLoadt();

	/**
	 * @param coolLoadt the coolLoadt to set
	 */
	public void setCoolLoadt(float coolLoadt);

	/**
	 * @return the inputFile
	 */
	public String getInputFile();

//	/**
//	 * @param localTime_array the localTime_array to set
//	 */
//	public void setLocalTime_array(Cell[] localTime_array);
	


	

	public void read(List<WeatherDataFinalForm> weatherData);
	
	
	
	
	
	
	
}
