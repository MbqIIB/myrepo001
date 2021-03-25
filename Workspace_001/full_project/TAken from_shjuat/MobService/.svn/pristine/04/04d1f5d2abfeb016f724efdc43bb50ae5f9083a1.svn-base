package com.npst.mobileservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.object.CustomerMandateHistoryDto;
import com.npst.mobileservice.object.MandatesHistory;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.MandatesHistoryEntity;

public class MandateHistoryDao {

	private static final Logger	log				= Logger.getLogger(MandateHistoryDao.class);
	
	
	
	
	private static final String MANDATEHISTORY = "select * from mandates_history where regId=:regid and MandateType =:mandateType and mandatestatus is not null and mandateValidityEnd <= TO_CHAR(sysdate, 'ddMMyyyy')";
	private static final String MANDATEHISTORYMANDATETYPE= "select * from mandates_history where regId=:regid"; // and ((MandateType='null') or (MandateType is null) or(MandateType='') and mandatestatus is not null and mandateValidityEnd <= TO_CHAR(sysdate, 'ddMMyyyy'))";
	
	private SessionFactory		sessionFactory	= getSessionFactory();
	
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<MandatesHistory>  mandateHistory(String regId,String mandateType) {
				
		Session session = null;
		log.info("Inside mandateHistory execute query method" );
		List<MandatesHistoryEntity> customerMandateHistoryDtos=new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			if(!StringUtils.isEmpty(mandateType)) {
				log.info("Inside mandateHistory execute query 1 method" );
				SQLQuery query = session.createSQLQuery(MANDATEHISTORY);
				query.addEntity(MandatesHistoryEntity.class);
				query.setLong(ConstantI.REGID, Long.valueOf(regId));
				query.setParameter("mandateType", mandateType);
				
				customerMandateHistoryDtos = query.list();
			}
			else {
				log.info("Inside mandateHistory execute query 2 method" );
				SQLQuery query = session.createSQLQuery(MANDATEHISTORYMANDATETYPE);
				query.addEntity(MandatesHistoryEntity.class);
				query.setParameter(ConstantI.REGID, regId);
				customerMandateHistoryDtos = query.list();
			}
			

		} catch (Exception ex) {
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ERROR >>>>>"+ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		log.info("Inside mandateHistory execute query return method="+customerMandateHistoryDtos );
		List<MandatesHistory> list=new ArrayList<>();
		MandatesHistory mandatesHistory=null;

		try{
			for(MandatesHistoryEntity obj:customerMandateHistoryDtos){
				//System.out.println("obj.getMandateAmountvalue()"+obj.getMandateAmountvalue());
				mandatesHistory=new MandatesHistory(obj);
				list.add(mandatesHistory);
			}
		}catch(Exception eee){
			log.info(">>>>>>>ERROR222222222222222222222");
		}

		log.info("Inside mandateHistory execute query return method="+list );
		
		return list;
	}
	
	protected SessionFactory getSessionFactory() {
		log.trace("");
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
}
