package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Dao.CoolingLoadDBDaoImpl;
import edu.unl.bsm.OutlierDetection.Data.WeatherDataWeekDays;



public class OutlierWeekDayProvider extends WeatherDataWeekDays implements OutlierModel{
	
	private List<Node[]> outlierModel = new ArrayList<Node[]>();

	public OutlierWeekDayProvider() throws BiffException, IOException {
		super();
	}
	
	
	public void setHistdata() throws ParseException {
		super.readHistWeatherData();
		int datasize = super.getHistWeatherData().getDataSize();
	}
	
	
	public List<Node[]> getOutlierModel(){
		return this.outlierModel;
	}
	
	public void setOutlierModel(int hour){
		Map<Date, String> dateMap = super.getHistWeatherData().getLocalTime();
		List<Date> dateList = new ArrayList<Date>(dateMap.keySet());
		Collections.sort(dateList);
		
		for(int i = 0; i < dateList.size(); i++){
			DateTime date = new DateTime(dateList.get(i));
			
			if(date.getDayOfWeek() >=1 && date.getDayOfWeek()<=5){
				if(date.getHourOfDay() == hour){
					Node[] nodes = new Node[3];
					float temp_t = super.getHistWeatherData().getT_t().get(date.toDate());
					float temp_CL = super.getHistWeatherData().getCoolLoad_t().get(date.toDate());
					nodes[1] = new Node(temp_t, temp_CL,date.toString());
					
					// t-1 exists
					if(dateList.contains(date.minusHours(1).toDate())){
						 temp_t = super.getHistWeatherData().getT_t().get(date.minusHours(1).toDate());
						 temp_CL = super.getHistWeatherData().getCoolLoad_t().get(date.minusHours(1).toDate());
						 nodes[0] = new Node(temp_t, temp_CL, date.minusHours(1).toString());
					}
					// t+1 exists
					if(dateList.contains(date.plusHours(1).toDate())){
						temp_t = super.getHistWeatherData().getT_t().get(date.plusHours(1).toDate());
						 temp_CL = super.getHistWeatherData().getCoolLoad_t().get(date.plusHours(1).toDate());
						 nodes[2] = new Node(temp_t, temp_CL, date.plusHours(1).toString());
					}
					outlierModel.add(nodes);
					
				}
			}
			
		}
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws BiffException, IOException, ParseException {
		// TODO Auto-generated method stub
		OutlierWeekDayProvider wd = new OutlierWeekDayProvider();
		wd.setHistdata();
		wd.setOutlierModel(1);
		List<Node[]> test = wd.getOutlierModel();
		for(Node[] el: test){
			for(int i = 0; i < el.length; i++){
				if(el[i] != null)
				System.out.print(el[i].getNodeName()+ " "+ el[i].getTemperature()+" "+el[i].getCoolingLoad()+ " ");
			}
			System.out.println();
		}
	}

}
