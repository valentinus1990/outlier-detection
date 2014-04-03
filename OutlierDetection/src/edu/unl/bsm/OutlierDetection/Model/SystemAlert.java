package edu.unl.bsm.OutlierDetection.Model;

import java.util.List;

public class SystemAlert {

	private int outlierLevelI;
	private int outlierLevelII;
	private int outlierLevelIII;
	private int outlierLevelIV ;
	private int systemAlertLevel;
	private static SystemAlert systemAlert = null;
	
	private SystemAlert(){
		this.outlierLevelI = 0;
		this.outlierLevelII = 0;
		this.outlierLevelIII = 0;
		this.outlierLevelIV = 0;
		this.systemAlertLevel = 0;
	}
	
	public static SystemAlert getInstance(){
		if(systemAlert == null){
			systemAlert = new SystemAlert();
		}
		return systemAlert;
	}
	
	public void reportAlert(List<Float> maxCDLOFs){
		if(maxCDLOFs == null || maxCDLOFs.size() < 1){
			throw new IllegalArgumentException();
		}
		for(float cdlof : maxCDLOFs){
			if(cdlof < 2 && cdlof >=1){
				this.outlierLevelI++;
			}else if(cdlof < 3 && cdlof >=2){
				this.outlierLevelII++;
			}else if(cdlof < 4 && cdlof >=3){
				this.outlierLevelIII++;
			}else if(cdlof >=4){
				this.outlierLevelIV++;
			}
		}
		
		if(this.outlierLevelI <= 4 && this.outlierLevelII <=2 && this.outlierLevelIII == 0 && this.outlierLevelIV == 0){
			setSystemAlertLevel(1);
		}else if((this.outlierLevelI <=8 && this.outlierLevelI > 4) && (this.outlierLevelII <= 4 && this.outlierLevelII >2)
				&& (this.outlierLevelIII <= 1) && this.outlierLevelIV == 0){
			setSystemAlertLevel(2);
		}else if((this.outlierLevelI <=13 && this.outlierLevelI > 8) && (this.outlierLevelII <= 7 && this.outlierLevelII >4)
				&& (this.outlierLevelIII <= 2 && this.outlierLevelIII > 1) && this.outlierLevelIV <= 1){
			setSystemAlertLevel(3);
		}else if((this.outlierLevelI <=18 && this.outlierLevelI > 13) && (this.outlierLevelII <= 10 && this.outlierLevelII >7)
				&& (this.outlierLevelIII <= 5 && this.outlierLevelIII > 2) && this.outlierLevelIV <= 2 && this.outlierLevelIV > 1){
			setSystemAlertLevel(4);
		}else{
			setSystemAlertLevel(5);
		}
		
	}

	/**
	 * @return the systemAlertLevel
	 */
	public int getSystemAlertLevel() {
		return systemAlertLevel;
	}

	/**
	 * @param systemAlertLevel the systemAlertLevel to set
	 */
	public void setSystemAlertLevel(int systemAlertLevel) {
		this.systemAlertLevel = systemAlertLevel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
