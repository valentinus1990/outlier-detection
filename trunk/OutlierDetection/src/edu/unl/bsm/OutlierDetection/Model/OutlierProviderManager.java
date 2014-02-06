package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class OutlierProviderManager {

	public OutlierProviderManager() {
		// TODO Auto-generated constructor stub
	}

	public OutlierModel createOutlierWD() throws BiffException, IOException{
		return new OutlierWeekDayProvider();
	}
	
	public OutlierModel createOutlerWE() throws BiffException, IOException{
		return new OutlierWeekDayProvider();
	}
	

}
