package com.npst.mobileservice.dao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.CustomerMandatesEntity;


public class CustomerMandateDao {

	private static final Logger	log				= Logger.getLogger(CustomerMandateDao.class);
	//private static final String MYMANDATESQL = "select * from customer_mandates where regid=:regid and mandateValidityEnd >= TO_CHAR(sysdate, 'ddMMyyyy') and mandateType=:mandateType";
	//private static final String MYMANDATESQL = "select * from customer_mandates where regid=:regid and mandateValidityEnd >= TO_CHAR(sysdate, 'ddMMyyyy') and mandateType=:mandateType";

	private static final String MYMANDATEMANDATETYPESQL= "select * from customer_mandates where regId=:regid and status=2 and MandateValidityEnd >=:todaydate";
	private static final String MYMANDATESQL = "select m from CustomerMandatesEntity m where m.regId = :regid and status=2 and MandateValidityEnd >=:todaydate";
	
	private static final String GETMANDATESFORPAYEE = "select m from CustomerMandatesEntity m where m.mandateUmn = :mandateUmn and m.custis=:custis and m.mandateSignValue is null";
	private SessionFactory		sessionFactory	= getSessionFactory();
	
	//@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public List<CustomerMandatesEntity>  myMandate(String regId,String mandateType) {
		Session session = null;
		List<CustomerMandatesEntity> customerMandateslist=new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			
			if(!StringUtils.isEmpty(mandateType)) {
				log.info("Inside if for mymandate");
				LocalDate today = LocalDate.now();
				String day=""+today.getDayOfMonth();
				day=day.length()==1?0+day:day;
				String month=""+today.getMonthValue();
				month=month.length()==1?0+month:month;
			    String date=day+month+today.getYear();
				customerMandateslist = session.createQuery(MYMANDATESQL).setParameter(ConstantI.REGID, Long.parseLong(regId)).setParameter("todaydate", date).list();
//				query.addEntity(CustomerMandates.class);
				//query.setParameter(ConstantI.REGID, regId);
				//query.setParameter("mandateType", mandateType);
				//customerMandateslist = query.list();
			}
	else {
		log.info("Inside else for mymandate");
		LocalDate today = LocalDate.now();
		String day=""+today.getDayOfMonth();
		day=day.length()==1?0+day:day;
		String month=""+today.getMonthValue();
		month=month.length()==1?0+month:month;
	    String date=day+month+today.getYear();
		     
			SQLQuery query = session.createSQLQuery(MYMANDATEMANDATETYPESQL);
				query.addEntity(CustomerMandatesEntity.class);
				query.setLong(ConstantI.REGID, Long.valueOf(regId));
				query.setString("todaydate", date);
			customerMandateslist = query.list();
			}

		} catch (Exception ex) {
			
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return customerMandateslist;
	}
	
	public List<CustomerMandatesEntity> getmandateofpayee(String umn, String cusrtis) {
		Session session = null;
		List<CustomerMandatesEntity> customerMandateslist=new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			customerMandateslist = session.createQuery(GETMANDATESFORPAYEE).setParameter("mandateUmn", umn).setParameter("custis", cusrtis).list();

		}
		catch (Exception e) {
	
		}
		finally {
			if (null != session) {
				session.close();
			}
		}
		return customerMandateslist;		
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
