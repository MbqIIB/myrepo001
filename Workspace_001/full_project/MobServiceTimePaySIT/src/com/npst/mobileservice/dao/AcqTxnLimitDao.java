package com.npst.mobileservice.dao;
import java.util.Date;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.AcqTxnLimitEntity;

public class AcqTxnLimitDao {
	private static final Logger log = Logger.getLogger(AcqTxnLimitDao.class);
	private static SessionFactory sessionFactory = HibernateListener.getSessionFactory();
	private static final String updateUserType = "update ACQUIRER_LIMIT_TXNS set USER_TYPE=:userType where MOBILE_NO=:mobileNo ";
	private static final String updatePay = "update ACQUIRER_LIMIT_TXNS set PAY_CNT=PAY_CNT+1 ,TXNID=:txnId where MOBILE_NO=:mobileNo ";
	private static final String updatePayWithFirstTxnDate = "update ACQUIRER_LIMIT_TXNS set PAY_CNT=PAY_CNT+1 ,TXNID=:txnId ,FIRST_PAY_S_TXNDATE=:creDate where MOBILE_NO=:mobileNo ";
	private static final String updateCollect = "update ACQUIRER_LIMIT_TXNS set COLLECT_CNT=COLLECT_CNT+1 ,TXNID=:txnId where MOBILE_NO=:mobileNo ";

	private static final AcqTxnLimitDao acqTxnLimitDao = new AcqTxnLimitDao();

	public static AcqTxnLimitDao getInstance() {
		return acqTxnLimitDao;
	}

	public AcqTxnLimitEntity findByMobileNo(long mobileNo) {
		Session session = null;
		AcqTxnLimitEntity ob = null;
		try {
			session = sessionFactory.openSession();
			ob = session.get(AcqTxnLimitEntity.class, mobileNo);
		} catch (Exception e) {
			log.error("causeMsg={} ,error={}"+ e.getMessage()+""+ e);
			throw e;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return ob;
	}

	public void saveUpdate(AcqTxnLimitEntity ob) {
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(ob);
			txn.commit();
		} catch (Exception e) {
			log.error("causeMsg={} ,error={}"+e.getMessage()+""+ e);
			// if (txn != null && txn.isActive()) {
			// txn.rollback();
			// }
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public boolean updateCollectCount(String mobileNo, String txnId) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updateCollect).setParameter("mobileNo", mobileNo).setParameter("txnId", txnId);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.error("error in update collect count limit {}"+ e);
		}finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	public boolean updatePayCount(String mobileNo, String txnId) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updatePay).setParameter("mobileNo", mobileNo).setParameter("txnId", txnId);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.error("error in update Pay count limit {}"+ e);
		}finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}
	
	public boolean updatePayCountAndFirstTxnDate(String mobileNo, String txnId) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updatePayWithFirstTxnDate).setParameter("mobileNo", mobileNo).setParameter("txnId", txnId).setParameter("creDate", new Date());
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.error("error in update Pay count limit {}"+ e);
		}finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	public boolean updateUserType(String mobileNo, int userType) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updateUserType).setParameter("mobileNo", mobileNo).setParameter("userType", userType);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.error("error in update Pay count limit {}"+e);
		}finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}
}