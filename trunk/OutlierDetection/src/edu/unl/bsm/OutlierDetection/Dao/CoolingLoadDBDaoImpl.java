package edu.unl.bsm.OutlierDetection.Dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Entity.ArxModelResult;
import edu.unl.bsm.OutlierDetection.Entity.ArxModelRun;
import edu.unl.bsm.OutlierDetection.Entity.ArxModelType;
import edu.unl.bsm.OutlierDetection.Entity.MissData;
import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;
import edu.unl.bsm.OutlierDetection.Entity.Prediction;
import edu.unl.bsm.OutlierDetection.Entity.RobustMeanSquaredError;
import edu.unl.bsm.OutlierDetection.FaultTolerant.FaultTolerant;
import edu.unl.bsm.OutlierDetection.Util.Utility;

/**
 * 
 * @author ZhongYin Zhang
 *
 */
public class CoolingLoadDBDaoImpl {
	private static final String PERSISTENCE_UNIT_NAME = "ARXBuffer";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	private static final String TEMPERATURE = "temperature";
	private static final String HUMIDITY = "humidity";
	
	public CoolingLoadDBDaoImpl(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	
	/**
	 * get arxmodel type according to the typeId
	 * @param typeId
	 * @return
	 */
	public ArxModelType getARXModelTypeByID(int typeId){
		Query query = em.createQuery("SELECT t FROM ArxModelType t where t.typeId = :typeId");
		query.setParameter("typeId", typeId);
		
		ArxModelType result = (ArxModelType) query.getSingleResult();
		
		System.out.println(result.getARX_name());
		return result;
	}
	
	/**
	 * get the latest running data according to the latest runId
	 * @return
	 */
	public ArxModelRun getLatestARXModelRun(){
		Query query = em.createQuery("SELECT t FROM ArxModelRun t ORDER BY t.runId DESC");
		
		ArxModelRun result = (ArxModelRun) query.setFirstResult(0).setMaxResults(1).getSingleResult();
		
		return result;
	}
	
	/**
	 * Insert arxModel run data into ARX_Model_Run table according to the typeId 
	 * @param typeId
	 * @return
	 * @throws ParseException
	 */
	public ArxModelRun addARXModelRunByID(int typeId) throws ParseException{
		ArxModelRun run = new ArxModelRun();

		Date date = new Date();
		Date now = Utility.converToDateHH(date);
		
		run.setTimeUpdate(now);
		
		DateTime dt = new DateTime(now);
		if(dt.getDayOfWeek() >= 1 && dt.getDayOfWeek() <=5){
			run.setWeekDay("weekday");
		}
		else{
			run.setWeekDay("weekend");
		}
		ArxModelType arxModelType = getARXModelTypeByID(typeId);
		run.setArxModelType(arxModelType);
		em.persist(run);
		
		return run;
	}
	
	/**
	 * Insert ArxModel result data into ARX_Model_Result table 
	 * @param type
	 * @param bIndex
	 * @param bValue
	 * @return
	 * @throws ParseException 
	 */
	public ArxModelResult addARXModelResultData(int type, int bIndex, double bValue, int hour) throws ParseException{
		ArxModelRun arxModelRun = getLatestARXModelRun();
		ArxModelResult result = new ArxModelResult();
		if(type == arxModelRun.getArxModelType().getTypeId()){
		
				
				result.setArxModelRun(arxModelRun);
				result.setBIndex(bIndex);
				result.setBValue(bValue);
				DateTime now  = new DateTime();
				DateTime time = now.withTime(hour, 0, 0, 0);
				Time hourTime = Utility.converToTimeHHMMSS(time.toDate());
				if(hour >=0 && hour <=23){
					result.setResultHour(hourTime);
				}else{
					throw new IllegalArgumentException("Hour should be between 0 and 23");
				}
				em.persist(result);
		}else{
			throw new IllegalArgumentException();
		}
		
		return result;
	}
	
	
	
	/**
	 * Query the b_value result data from arx_model_result
	 * @param typeId
	 * @param hour
	 * @return
	 * @throws ParseException 
	 */
	public List<Double> getARXModelCofficientResult(int typeId, int hour, boolean weekday) throws ParseException{
	
		Query queryFirst = em.createNativeQuery("SELECT arx_model_result_b_value FROM arx_model_result " +
				"JOIN arx_model_run ON arx_model_result.arx_model_run_id = arx_model_run.arx_model_run_id " +
				"JOIN arx_model_type ON arx_model_run.arx_model_type_id = arx_model_type.arx_model_type_id " +
				"WHERE arx_model_type.arx_model_type_id = ? AND arx_model_result.arx_model_result_hour = ? AND arx_model_run.arx_model_run_week_day = ? " +
				"ORDER BY arx_model_run.arx_model_run_id desc " +
				"LIMIT 0, 26 ");
		Query querySecond = em.createNativeQuery("SELECT arx_model_result_b_value FROM arx_model_result " +
				"JOIN arx_model_run ON arx_model_result.arx_model_run_id = arx_model_run.arx_model_run_id " +
				"JOIN arx_model_type ON arx_model_run.arx_model_type_id = arx_model_type.arx_model_type_id " +
				"WHERE arx_model_type.arx_model_type_id = ? AND arx_model_result.arx_model_result_hour = ? AND arx_model_run.arx_model_run_week_day = ? " +
				"ORDER BY arx_model_run.arx_model_run_id desc " +
				"LIMIT 0, 17 ");
		List<Double> result = new ArrayList<Double>();
		
		DateTime now  = new DateTime();
		DateTime time = now.withTime(hour, 0, 0, 0);
		Time hourTime = Utility.converToTimeHHMMSS(time.toDate());
		
		if(typeId!= 7){
			queryFirst.setParameter(1, typeId);
			queryFirst.setParameter(2, hourTime);
			if(weekday){
				queryFirst.setParameter(3, "weekday");
			}
			else{
				queryFirst.setParameter(3, "weekend");
			}
			result = queryFirst.getResultList();
		}else{
			querySecond.setParameter(1, typeId);
			querySecond.setParameter(2, hourTime);
			if(weekday){
				querySecond.setParameter(3, "weekday");
			}
			else{
				querySecond.setParameter(3, "weekend");
			}
			result = querySecond.getResultList();
		}
		
		
		if(Utility.DEBUG){
			System.out.println("LocalDBDaoImpl.java : getResult()");
			System.out.println("ARXModeType" + typeId + " ,hour: " + hour);
			System.out.println("b: " + result);
		}
		

		return result;
	}
	
	/**
	 * Retrieve missing data in terms of specific date
	 * @param date
	 * @return
	 */
	private List<MissData> getMissDateByData(Timestamp date){
		Query query = em.createQuery("SELECT t FROM MissData t WHERE t.missDatetime = :date");
		query.setParameter("date", date);
		List<MissData> result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Retrieve all missing data
	 * @return
	 */
	public List<MissData> queryAllMissData(){
		Query query = em.createQuery("SELECT t FROM MissData t");
		List<MissData> result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Get Historical missing data from PermanentHistTemp Table
	 * @return Historical Missing data
	 * @throws ParseException
	 */
	public List<Map<Timestamp, Float>> getHistMissData() throws ParseException{
		FaultTolerant ft =  new FaultTolerant();
		List<Timestamp> missDate = ft.combineHistMissData();
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();	
		Map<Timestamp, Float> T_t = new HashMap<Timestamp, Float>();
		Map<Timestamp, Float> RH_t = new HashMap<Timestamp, Float>();
		
		
		for(Timestamp element: missDate){
//			System.out.println(element);
			
			List<PermanentHistoryData> tempT = dao.getPermanentHistTempData(element);
			
			if(tempT.size() == 0){
				
				T_t.put(Utility.converToDateHH(element), (float) 0);
				
			}else{
				
				for(PermanentHistoryData data_T : tempT){
					T_t.put(Utility.converToDateHH(data_T.getId().getHistDateTime()), data_T.getHistValue());
				}
			}
			
			
			List<PermanentHistoryData> tempRH = dao.getPermanentHistRHData(element);
			if(tempRH.size() == 0){
				RH_t.put(Utility.converToDateHH(element), (float)0);
			}else{
				for(PermanentHistoryData data_RH : tempRH){
					RH_t.put(Utility.converToDateHH(data_RH.getId().getHistDateTime()), data_RH.getHistValue());
				}
			}
			
			
			
		}
		if(Utility.DEBUG){
			System.out.println("LocalDBDaoImpl.java: getHistMissData()");
			System.out.println("T_t size: " + T_t.size());
			System.out.println("RH_t size: " + RH_t.size());
		}
		
		
		List<Map<Timestamp, Float>> result = new ArrayList<Map<Timestamp, Float>>();
		result.add(T_t);
		result.add(RH_t);
		
		return result;
	}
	
	
	/**
	 * Get last week Missing data from PermanentHistTemp Table.
	 * This method is only used to for setting up forecast data structure
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<Map<Timestamp, Float>> getLastWeekMissData() throws ParseException{
		FaultTolerant ft =  new FaultTolerant();
		List<Timestamp> missDate = ft.getLastWeekMissData();
		ARCHIVE_DBDaoImpl dao = new ARCHIVE_DBDaoImpl();	
		Map<Timestamp, Float> T_t = new HashMap<Timestamp, Float>();
		Map<Timestamp, Float> RH_t = new HashMap<Timestamp, Float>();
		
		
		for(Timestamp element: missDate){
//			System.out.println(element);
			
			List<PermanentHistoryData> tempT = dao.getPermanentHistTempData(element);
			if(tempT.size() == 0){
				
				T_t.put(Utility.converToDateHH(element), (float) 0);
				
			}else{
				
				for(PermanentHistoryData data_T : tempT){
					T_t.put(Utility.converToDateHH(data_T.getId().getHistDateTime()), data_T.getHistValue());
				}
			}
			
			
			List<PermanentHistoryData> tempRH = dao.getPermanentHistRHData(element);
			if(tempRH.size() == 0){
				RH_t.put(Utility.converToDateHH(element), (float)0);
			}else{
				for(PermanentHistoryData data_RH : tempRH){
					RH_t.put(Utility.converToDateHH(data_RH.getId().getHistDateTime()), data_RH.getHistValue());
				}
			}
			
			
			
		}
		
		if(Utility.DEBUG){
			System.out.println("LocalDBDaoImpl.java: getLastWeekMissData()");
			System.out.println("T_t size: " + T_t.size());
			System.out.println("RH_t size: " + RH_t.size());
		}
		
		
		List<Map<Timestamp, Float>> result = new ArrayList<Map<Timestamp, Float>>();
		result.add(T_t);
		result.add(RH_t);
		
		return result;
	}
	
	/**
	 * Add missing data into MissData table
	 * @param date
	 * @param T_t
	 * @param RH_t
	 */
	public void addMissData(Timestamp date, Map<Timestamp,Float> T_t, Map<Timestamp, Float> RH_t){
		
		MissData missData = new MissData();
		
		/*
		 * if the missing data already existed in the table, ignore it. Otherwise, insert it into the table
		 */
		if(getMissDateByData(date).size() == 0){
			missData.setMissDatetime(date);
			missData.setMiss_T_t(T_t.get(date));
			missData.setMiss_RH_t(RH_t.get(date));
			em.persist(missData);
		}
		
	}
	
	/**
	 * Insert the prediction data into Prediction table
	 * @param targetHour
	 * @param preClValue
	 */
	public void addPrediction(Timestamp targetHour, float preClValue, int arxType, double rmse){
		Prediction pre = new Prediction();
		DateTime tempNow = new DateTime();
		Timestamp now = new Timestamp(tempNow.getMillis());
		pre.setPreCollectTime(now);
		pre.setPreTargetTime(targetHour);
		pre.setPreClValue(preClValue);
		pre.setArxType(arxType);
		pre.setPreRobustMSE(rmse);
		em.persist(pre);
		
	}
	
	/**
	 * Insert Robust Mean Squared Error to Robust_Mean_Squared_Error
	 * @param type
	 * @param value
	 */
	public void addRobustMeanSquaredError(int type, double value){
		RobustMeanSquaredError rmse = new RobustMeanSquaredError();
		DateTime tempNow  = new DateTime();
		Timestamp now = new Timestamp(tempNow.getMillis());
		rmse.setRobustMeanSquaredErrorArxModelTypeId(type);
		rmse.setRobustMeanSquaredErrorTimeUpdate(now);
		rmse.setRobustMeanSquaredErrorValue(value);
		em.persist(rmse);
		
	}
	
	/**
	 * get predicted value from prediction table
	 * @return
	 */
	public List<Object[]> getPredictedValue(){
		Query query = em.createNativeQuery("SELECT PREDICTION_TARGET_TIME, PREDICTION_COOLING_LOAD_VALUE FROM PREDICTION " +
				"	WHERE PREDICTION_TARGET_TIME > CURRENT_TIME " +
				"	ORDER BY PREDICTION_ID DESC " +
				"	LIMIT 0, 48 ");
		List<Object[]> result = query.getResultList();
		
		return result;
	}
	
	/**
	 * get ROBUST_MEAN_SQUARED_ERROR_VALUE from ROBUST_MEAN_SQUARED_ERROR
	 * @return
	 */
	public List<Double> getRobustMeanSquaredError(){
		Query query = em.createNativeQuery("SELECT ROBUST_MEAN_SQUARED_ERROR_VALUE FROM robust_mean_squared_error " +
				"   ORDER BY ROBUST_MEAN_SQUARED_ERROR_ID DESC " +
				"	LIMIT 0, 7");
		
		List<Double> result = query.getResultList();
		return result;
	}
	
	/**
	 * query Prediction based on current hour.
	 * @return
	 */
	public List<Object[]> getPredictionForCurrentHour(){
		Query query = em.createNativeQuery("SELECT * FROM prediction " +
				"WHERE DATE(PREDICTION_TARGET_TIME) = CURRENT_DATE AND HOUR(PREDICTION_TARGET_TIME) = HOUR(CURRENT_TIME) " +
				"ORDER BY PREDICTION_ID DESC");
		List<Object[]> result = query.getResultList();
		
		return result;
	}
	
	
	public EntityManager getEntityManager(){
		return this.em;
	}
	
	
	

}
