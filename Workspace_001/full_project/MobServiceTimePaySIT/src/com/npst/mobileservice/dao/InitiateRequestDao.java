package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.InitiateRequest;

public class InitiateRequestDao {

	private static final Logger log = Logger.getLogger(InitiateRequestDao.class);
	
	private SessionFactory sessionFactory = getSessionFactory();
	
	public boolean validateRequest(String requestid) {
		log.info("validate Request for given requestId[" + requestid + "]");
		Session session = null;
		InitiateRequest results = null;
		boolean flag = false;
		try {
			String sql = "select * from initiateRequest where requestId=:requestId";
			log.info("SQL Query for search request:{}"+sql);
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(InitiateRequest.class);
			query.setParameter("requestId", requestid);
			results = (InitiateRequest)query.uniqueResult();
			if (results!=null) {
				flag=true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			log.info("Something went to wrong while searching data from InitiateRequest="+e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	
	public boolean insertRequest(InitiateRequest initiateRequest) {
		log.trace("Inserting data for initiate request table" + initiateRequest);
		Session session = null;
		Transaction transaction = null;
		boolean flag = true;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(initiateRequest);
			transaction.commit();
			flag = false;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("Something went to wrong while inserting data into InitiateRequest="+e);
		} finally {
			session.close();
		}
		return flag;
		
	}
	
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("HibernateListener");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
}
