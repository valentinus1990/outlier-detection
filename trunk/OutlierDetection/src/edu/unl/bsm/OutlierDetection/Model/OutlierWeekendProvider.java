package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Data.WeatherDataWeekend;



public class OutlierWeekendProvider extends WeatherDataWeekend implements OutlierModel{
	
	private List<Node[]> outlierModel = new ArrayList<Node[]>();

	public OutlierWeekendProvider() throws BiffException, IOException {
		super();
	}

	public void setHistdata() throws ParseException {
		super.readHistWeatherData();
		int datasize = super.getHistWeatherData().getDataSize();
	}
	
	
	public List<Node[]> getOutliserModel(){
		return this.outlierModel;
	}
	
	public void setOutlierModel(int hour){
		Map<Date, String> dateMap = super.getHistWeatherData().getLocalTime();
		List<Date> dateList = new ArrayList<Date>(dateMap.keySet());
		Collections.sort(dateList);
		
		for(int i = 0; i < dateList.size(); i++){
			DateTime date = new DateTime(dateList.get(i));
			
			if(date.getDayOfWeek() >=6 && date.getDayOfWeek()<=7){
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
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
