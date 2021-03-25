package com.npst.mobileservice.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.ReqRespAuthMandateEntity;
import com.npst.upi.hor.Reqrespauthdetails;

public class ReqRespAuthMandatedetailsHome {

	private static final Logger log = Logger.getLogger(ReqRespAuthMandatedetailsHome.class);
	private static final String PENDINGCOLLECTSQL_COUNT = "select count(1) cnt_ from req_resp_auth_mandate where regid=:regid and txnType= 'COLLECT' and ((RespResult='null') or (RespResult is null)) AND RespInsert is null AND ((TxnConfOrgStatus='null') or (TxnConfOrgStatus is null))";
	private static final String PENDINGCOLLECTSQL = "select * from req_resp_auth_mandate where regid=:regid and ((mandateType='null') or (mandateType is null) or (mandateType='') or (mandateType<>'ASBA')) and TxnInitiatedBy='PAYEE')";

	private static final String PENDINGCOLLECTMANDATESQL = "select * from req_resp_auth_mandate where regid=:regid and TxnInitiatedBy='PAYEE' and ((RespResult='null') or (RespResult is null)) AND RespInsert is null AND ((TxnConfOrgStatus='null') or (TxnConfOrgStatus is null))";

	private SessionFactory sessionFactory = getSessionFactory();
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ReqRespAuthMandateEntity> getPendingMandateCollect(long regId,String mandateType) {
		Session session = null;
		List<ReqRespAuthMandateEntity> reqRespAuthMandates = new ArrayList<>();
		try {
			log.trace("fetching ReqRespAuthMandate records of pending Mandate collet request for regId :{}"+regId);
			session = this.sessionFactory.openSession();
			
			if(!StringUtils.isEmpty(mandateType)) {
				SQLQuery query = session.createSQLQuery(PENDINGCOLLECTMANDATESQL);
				query.addEntity(ReqRespAuthMandateEntity.class);
				query.setLong(ConstantI.REGID, Long.valueOf(regId));
				//query.setParameter("mandateType", mandateType);
				reqRespAuthMandates = query.list();
			}
			else {
				SQLQuery query = session.createSQLQuery(PENDINGCOLLECTSQL);
				query.addEntity(ReqRespAuthMandateEntity.class);
				query.setLong(ConstantI.REGID, Long.valueOf(regId));
				reqRespAuthMandates = query.list();
			}
			log.trace("record list : {}"+ reqRespAuthMandates);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(),ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return reqRespAuthMandates;
	}

	@SuppressWarnings({ "deprecation" })
	public int getPendingMandateCollectCountByRegId(long regId) {
		Session session = null;
		int count = 0;
		try {
			log.info("Inside getPendingMandateCollectCountByRegId for fetching count of pending Mandate collect request for regId :{}"+regId);
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(PENDINGCOLLECTSQL_COUNT);
			query.setParameter(ConstantI.REGID, regId);
			query.addScalar("cnt_",IntegerType.INSTANCE);
			count =(int)query.uniqueResult();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return count;
	}

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
}
