package com.npst.mobileservice.dao;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.ReqRespAuthMandatePayeesEntity;

public class ReqRespAuthMandatedetailsPayeesHome {

	private static final Logger	log				= Logger.getLogger(ReqRespAuthMandatedetailsPayeesHome.class);
	private static final String PENDINGCOLLECTSQL = "select * from REQ_RESP_AUTH_MANDATE_PAYEES where txnId=:txnId";

	private SessionFactory		sessionFactory	= getSessionFactory();
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ReqRespAuthMandatePayeesEntity> getPayee(String txnId) {
		log.trace("");
		Session session = null;
		List<ReqRespAuthMandatePayeesEntity> list = new ArrayList<>();
		try {
			log.trace("fetching ReqrespauthdetailsPayees records for txnId :{}"+txnId);
			
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(PENDINGCOLLECTSQL);
			query.addEntity(ReqRespAuthMandatePayeesEntity.class);
			query.setParameter("txnId", txnId);
			list = query.list();

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
			}
		}
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
