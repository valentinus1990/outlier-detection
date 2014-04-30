package edu.unl.bsm.OutlierDetection.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.unl.bsm.OutlierDetection.Entity.OBJECTS;

public class GREEDODaoImpl implements IGREEDODao{

	private static final String PERSISTENCE_UNIT_NAME = "GREEDO";
	private static EntityManagerFactory factory;
	private EntityManager em;
	
	
	
	public GREEDODaoImpl(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	
	public EntityManager getEntityManager(){
		return this.em;
	}
	/**
	 * get the combination keys for Cooling Load
	 */
	@Override
	public OBJECTS getCLPK(){
		Query query = em.createNamedQuery("getCLPK");
		OBJECTS result = (OBJECTS) query.getSingleResult();
		return result;
	}
	
	/**
	 * get the combination keys for Temperature
	 */
	@Override
	public OBJECTS getTempPK() {
		Query query = em.createNamedQuery("getTempPK");
		OBJECTS result = (OBJECTS) query.getSingleResult();
		return result;
	}

	/**
	 * get the combination keys for RH
	 */
	@Override
	public OBJECTS getRHPK() {
		Query query = em.createNamedQuery("getRHPK");
		OBJECTS result = (OBJECTS) query.getSingleResult();
		return result;
	}
	
	public List<String> getEmailRecipients(){
		Query query = em.createNativeQuery("SELECT u.EMAIL_ADDRESS " + 
			      "FROM " +
			      "OBJ_USER AS u " +
			      "JOIN NOTIFICATION_SUBSCRIBERS AS s ON s.OBJ_ID_TOP = u.OBJ_ID_TOP " +
			      "JOIN NOTIFICATION_PUBLISHERS AS p ON s.PROJECT_ID = p.ID " +
			      "WHERE "+
			      "p.NAME = 'NEW_USER_CREATE_REQUESTS'");
		
		List<String> result = query.getResultList();
		
		System.out.println(result);
		
		return result;
	}
	
	
	public List<String> getEmailRecipientsTest(){
		Query query = em.createNativeQuery("SELECT u.EMAIL_ADDRESS FROM OBJ_USER AS u WHERE u.USERNAME='zzhang10'");
		
		List<String> result = query.getResultList();
		
//		System.out.println(result);
		
		return result;
	}
	
	
	public static void main(String[] args){
		GREEDODaoImpl gdi = new GREEDODaoImpl();
		gdi.getEmailRecipients();
	}
	

}
