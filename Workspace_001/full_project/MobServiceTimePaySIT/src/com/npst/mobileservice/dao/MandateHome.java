package com.npst.mobileservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.MandatesHistoryEntity;


public class MandateHome {
	private static final Logger log = Logger.getLogger(MandateHome.class);
	private SessionFactory sessionFactory = getSessionFactory();
	Session session = null;
	
	private static final String MANDATE_QUERY = "select * from MANDATES_HISTORY where regid=:regid";// and status=2

	public List<MandatesHistoryEntity> getAllmandates(long regId) {
		
		List<MandatesHistoryEntity> list = new ArrayList<>();
		try {
			log.trace("fetching Mandates records for regId :{}"+regId);
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(MANDATE_QUERY);
			query.addEntity(MandatesHistoryEntity.class);
			query.setParameter(ConstantI.REGID, regId);
			list = query.list();
			log.trace("record list : {}"+list);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return list;

	}
	private SessionFactory getSessionFactory() {
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
	


}
